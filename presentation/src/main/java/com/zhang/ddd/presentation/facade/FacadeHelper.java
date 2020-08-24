package com.zhang.ddd.presentation.facade;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.Vote;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteResourceType;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteType;
import com.zhang.ddd.domain.aggregate.vote.repository.VoteRepository;
import com.zhang.ddd.presentation.facade.assembler.QuestionAssembler;
import com.zhang.ddd.presentation.facade.assembler.UserAssembler;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.post.QuestionDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import com.zhang.ddd.presentation.web.security.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacadeHelper {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoteRepository voteRepository;

    public List<QuestionDto> wrapAnswerQuestion(List<AnswerDto> answerDtos) {
        Map<Long, QuestionDto> questionDtos = questionRepository
                .findByIds(answerDtos.stream().map(AnswerDto::getParentId).collect(Collectors.toList()))
                .stream().map(QuestionAssembler::toDTO).collect(Collectors.toMap(QuestionDto::getId, e -> e,
                        (e1, e2) -> {
                    return e1;
                }));

        fillQuestionUsers(questionDtos.values().stream().collect(Collectors.toList()));

        List<QuestionDto> res = answerDtos.stream().map(e -> {
            QuestionDto q = questionDtos.get(e.getParentId());
            q = q.copy();
            q.setCover(e);
            return q;
        }).collect(Collectors.toList());

        return res;
    }

    public void fillQuestionUsers(List<QuestionDto> questionDtos) {

        Map<Long, UserDto> users = getUserIdMapping(questionDtos.stream()
                .map(QuestionDto::getAuthorId).collect(Collectors.toList()));

        questionDtos.stream().forEach(e -> e.setAuthor(users.get(e.getAuthorId())));
    }

    public void fillAnswerUsers(List<AnswerDto> answerDtos) {

        Map<Long, UserDto> users = getUserIdMapping(answerDtos.stream()
                .map(AnswerDto::getAuthorId).collect(Collectors.toList()));

        answerDtos.stream().forEach(e -> e.setAuthor(users.get(e.getAuthorId())));
    }

    public void fillQuestionVotes(List<QuestionDto> questionDtos) {
        UserDto me = LoginUtil.getCurrentUser();
        if (me == null){
            return;
        }
        Map<Long, QuestionDto> qm = questionDtos
                .stream().collect(Collectors.toMap(QuestionDto::getId, e -> e));

        List<Vote> votes = voteRepository.findByResourceIds(me.getId(),
                questionDtos.stream().map(QuestionDto::getId).collect(Collectors.toList()),
                VoteResourceType.QUESTION);

        votes.stream().forEach(e -> {
            QuestionDto questionDto = qm.get(e.getResourceId());
            questionDto.setUpvoted(e.getVoteType() == VoteType.UPVOTE);
        });
    }

    public void fillAnswerVotes(List<AnswerDto> answerDtos) {
        UserDto me = LoginUtil.getCurrentUser();
        if (me == null){
            return;
        }
        Map<Long, AnswerDto> am = answerDtos
                .stream().collect(Collectors.toMap(AnswerDto::getId, e -> e));

        List<Vote> votes = voteRepository.findByResourceIds(me.getId(),
                answerDtos.stream().map(AnswerDto::getId).collect(Collectors.toList()),
                VoteResourceType.ANSWER);

        votes.stream().forEach(e -> {
            AnswerDto answerDto = am.get(e.getResourceId());
            answerDto.setUpvoted(e.getVoteType() == VoteType.UPVOTE);
            answerDto.setDownvoted(e.getVoteType() == VoteType.DOWNVOTE);
        });
    }

    public Map<Long, UserDto> getUserIdMapping(List<Long> ids) {

        return userRepository.findByIds(ids)
                .stream()
                .collect(Collectors.toMap(User::getId, e -> UserAssembler.toDTO(e)));
    }
}
