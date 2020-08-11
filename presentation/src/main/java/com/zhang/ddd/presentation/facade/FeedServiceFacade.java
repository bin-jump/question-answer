package com.zhang.ddd.presentation.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.zhang.ddd.domain.aggregate.favor.entity.Feed;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedType;
import com.zhang.ddd.domain.aggregate.favor.repository.FavorPaging;
import com.zhang.ddd.domain.aggregate.favor.repository.FeedRepository;
import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.repository.AnswerRepository;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.presentation.facade.FacadeHelper;
import com.zhang.ddd.presentation.facade.assembler.AnswerAssembler;
import com.zhang.ddd.presentation.facade.assembler.FeedAssembler;
import com.zhang.ddd.presentation.facade.assembler.QuestionAssembler;
import com.zhang.ddd.presentation.facade.assembler.UserAssembler;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.post.FeedDto;
import com.zhang.ddd.presentation.facade.dto.post.QuestionDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedServiceFacade {

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FacadeHelper facadeHelper;


    public List<FeedDto> findFeed(String userId, String cursor, int size) {
        List<Feed> feeds = feedRepository.getUserFeed(userId, new FavorPaging(cursor, size));

        Map<String, Feed> feedResourceMap = feeds.stream()
                .collect(Collectors.toMap(Feed::getResourceId, e -> e));

        List<FeedDto> qfeeds = fillFeedQuestions(feedResourceMap);
        List<FeedDto> afeeds = fillFeedAnswers(feedResourceMap);
        List<FeedDto> ufeeds = fillFeedUsers(feedResourceMap);

        Map<String, FeedDto> feedDtos = Stream.of(qfeeds, afeeds, ufeeds)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(FeedDto::getId, e -> e));

        List<FeedDto> res = new ArrayList<>();
        feeds.stream().forEach(e -> {
            res.add(feedDtos.get(e.getId()));
        });

        return res;
    }

    private List<FeedDto> fillFeedQuestions(Map<String, Feed> feeds) {
        List<String> qids = feeds.values()
                .stream().filter(e -> e.getFeedType() == FeedType.QUESTION)
                .map(Feed::getResourceId)
                .collect(Collectors.toList());
        if (qids.size() == 0) {
            return new ArrayList<>();
        }
        List<QuestionDto> questionDtos = questionRepository.findByIds(qids)
                .stream().map(QuestionAssembler::toDTO)
                .collect(Collectors.toList());
        facadeHelper.fillQuestionUsers(questionDtos);

        Map<String, UserDto> creators = userRepository.findByIds(feeds.values().stream()
        .map(Feed::getCreatorId).collect(Collectors.toList()))
                .stream().map(UserAssembler::toDTO).collect(Collectors.toMap(UserDto::getId, e -> e));

        List<FeedDto> res = questionDtos.stream()
                .map(e -> {
                    Feed feed = feeds.get(e.getId());
                    FeedDto feedDto = FeedAssembler.toDTO(feed, creators.get(feed.getCreatorId()));
                    feedDto.setTarget(e);
                    return feedDto;
                }).collect(Collectors.toList());

        return res;
    }

    private List<FeedDto> fillFeedAnswers(Map<String, Feed> feeds) {
        List<String> aids = feeds.values()
                .stream().filter(e -> e.getFeedType() == FeedType.ANSWER)
                .map(Feed::getResourceId)
                .collect(Collectors.toList());
        if (aids.size() == 0) {
            return new ArrayList<>();
        }
        List<AnswerDto> answerDtos = answerRepository.findByIds(aids)
                .stream().map(AnswerAssembler::toDTO)
                .collect(Collectors.toList());

        facadeHelper.fillAnswerUsers(answerDtos);
        List<QuestionDto> questionDtos = facadeHelper.wrapAnswerQuestion(answerDtos);

        List<FeedDto> res = questionDtos.stream()
                .map(e -> {
                    AnswerDto ans = e.getCover();
                    Feed feed = feeds.get(ans.getId());
                    FeedDto feedDto = FeedAssembler.toDTO(feed, ans.getAuthor());
                    feedDto.setTarget(e);
                    return feedDto;
                }).collect(Collectors.toList());

        return res;
    }

    private List<FeedDto> fillFeedUsers(Map<String, Feed> feeds) {
        List<String> uids = feeds.values()
                .stream().filter(e -> e.getFeedType() == FeedType.USER)
                .map(Feed::getResourceId)
                .collect(Collectors.toList());
        if (uids.size() == 0) {
            return new ArrayList<>();
        }
        List<UserDto> userDtos = userRepository.findByIds(uids)
                .stream().map(UserAssembler::toDTO)
                .collect(Collectors.toList());

        Map<String, UserDto> creators = userRepository.findByIds(feeds.values().stream()
            .map(Feed::getCreatorId).collect(Collectors.toList()))
                .stream().map(UserAssembler::toDTO)
                .collect(Collectors.toMap(UserDto::getId, e -> e));

        List<FeedDto> res = userDtos.stream()
                .map(e -> {
                    Feed feed = feeds.get(e.getId());
                    FeedDto feedDto = FeedAssembler.toDTO(feed, creators.get(feed.getCreatorId()));
                    feedDto.setTarget(e);
                    return feedDto;
                }).collect(Collectors.toList());

        return res;
    }

}
