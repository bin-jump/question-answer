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


    public List<FeedDto> findFeed(Long userId, Long cursor, int size) {

        List<Feed> feeds = feedRepository.getUserFeed(userId, new FavorPaging(cursor, size));

        List<FeedDto> qfeeds = fillFeedQuestions(feeds);
        List<FeedDto> afeeds = fillFeedAnswers(feeds);
        List<FeedDto> ufeeds = fillFeedUsers(feeds);

        Map<Long, FeedDto> feedDtos = Stream.of(qfeeds, afeeds, ufeeds)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(FeedDto::getId, e -> e, (e1, e2) -> {
                    return e1;
                }));

        List<FeedDto> res = new ArrayList<>();
        feeds.stream().forEach(e -> {
            res.add(feedDtos.get(e.getId()));
        });

        return res;
    }

    private List<FeedDto> fillFeedQuestions(List<Feed> feeds) {
        List<Long> qids = feeds
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

        Map<Long, UserDto> creators = userRepository.findByIds(feeds.stream()
        .map(Feed::getCreatorId).collect(Collectors.toList()))
                .stream().map(UserAssembler::toDTO).collect(Collectors.toMap(UserDto::getId, e -> e));

        Map<Long, QuestionDto> questionDtoMap = questionDtos
                .stream().collect(Collectors.toMap(QuestionDto::getId, e -> e, (e1, e2) -> {
                    return e1;
                }));

        List<FeedDto> res = feeds.stream()
                .filter(e -> e.getFeedType() == FeedType.QUESTION)
                .map(e -> {
                    FeedDto feedDto = FeedAssembler.toDTO(e, creators.get(e.getCreatorId()));
                    feedDto.setTarget(questionDtoMap.get(e.getResourceId()));
                    return feedDto;
                }).collect(Collectors.toList());

        return res;
    }

    private List<FeedDto> fillFeedAnswers(List<Feed> feeds) {
        List<Long> aids = feeds
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
        Map<Long, QuestionDto> questionDtoMap = questionDtos.stream()
                .collect(Collectors.toMap(e -> e.getCover().getId(), e -> e, (e1, e2) -> {
                    return e1;
                }));

        List<FeedDto> res = feeds.stream()
                .filter(e -> e.getFeedType() == FeedType.ANSWER)
                .map(e -> {
                    QuestionDto questionDto = questionDtoMap.get(e.getResourceId());
                    FeedDto feedDto = FeedAssembler.toDTO(e, questionDto.getCover().getAuthor());
                    feedDto.setTarget(questionDto);
                    return feedDto;
                }).collect(Collectors.toList());

        return res;
    }

    private List<FeedDto> fillFeedUsers(List<Feed> feeds) {
        List<Long> uids = feeds
                .stream().filter(e -> e.getFeedType() == FeedType.USER)
                .map(Feed::getResourceId)
                .collect(Collectors.toList());
        if (uids.size() == 0) {
            return new ArrayList<>();
        }
        List<UserDto> userDtos = userRepository.findByIds(uids)
                .stream().map(UserAssembler::toDTO)
                .collect(Collectors.toList());

        Map<Long, UserDto> userDtoMap = userDtos.stream()
                .collect(Collectors.toMap(UserDto::getId, e -> e, (e1, e2) -> {
                    return e1;
                }));

        Map<Long, UserDto> creators = userRepository.findByIds(feeds.stream()
            .map(Feed::getCreatorId).collect(Collectors.toList()))
                .stream().map(UserAssembler::toDTO)
                .collect(Collectors.toMap(UserDto::getId, e -> e, (e1, e2) -> {
                    return e1;
                }));

        List<FeedDto> res = feeds.stream()
                .filter(e -> e.getFeedType() == FeedType.USER)
                .map(e -> {
                    FeedDto feedDto = FeedAssembler.toDTO(e, creators.get(e.getCreatorId()));
                    feedDto.setTarget(userDtoMap.get(e.getResourceId()));
                    return feedDto;
                }).collect(Collectors.toList());

        return res;
    }

}
