package com.zhang.ddd.application.service;

import com.zhang.ddd.domain.aggregate.post.entity.Question;
import com.zhang.ddd.domain.aggregate.post.repository.AnswerRepository;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.aggregate.post.service.AnswerDomainService;
import com.zhang.ddd.domain.aggregate.post.service.QuestionDomainService;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.*;
import com.zhang.ddd.domain.aggregate.vote.service.VoteDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoteApplicationService {

    @Autowired
    VoteDomainService voteDomainService;

    @Autowired
    QuestionDomainService questionDomainService;

    @Autowired
    AnswerDomainService answerDomainService;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    // skip checking question existence as question will be updated
    @Transactional
    public VoteResult voteQuestion(String voterId, String resourceId, boolean upVote) {

        VoteType voteType = upVote ? VoteType.UPVOTE : VoteType.DOWNVOTE;
        VoteResult voteResult = voteDomainService.vote(voterId, resourceId, VoteResourceType.QUESTION, voteType);

        questionDomainService.questionVoted(resourceId,
                voteResult.getUpvoteDiff(), voteResult.getDownvoteDiff());

        return voteResult;
    }

    // skip checking answer existence as answer will be updated
    @Transactional
    public VoteResult voteAnswer(String voterId, String resourceId, boolean upVote) {

        VoteType voteType = upVote ? VoteType.UPVOTE : VoteType.DOWNVOTE;
        VoteResult voteResult = voteDomainService.vote(voterId, resourceId, VoteResourceType.ANSWER, voteType);

        answerDomainService.answerVoted(resourceId,
                voteResult.getUpvoteDiff(), voteResult.getDownvoteDiff());

        return voteResult;
    }

    // skip checking question existence as question will be updated
    @Transactional
    public VoteResult unvoteQuestion(String voterId, String resourceId) {

        VoteResult voteResult = voteDomainService.unvote(voterId, resourceId, VoteResourceType.QUESTION);

        questionDomainService.questionVoted(resourceId,
                voteResult.getUpvoteDiff(), voteResult.getDownvoteDiff());

        return voteResult;
    }

    // skip checking answer existence as answer will be updated
    @Transactional
    public VoteResult unvoteAnswer(String voterId, String resourceId) {

        VoteResult voteResult = voteDomainService.unvote(voterId, resourceId, VoteResourceType.ANSWER);

        answerDomainService.answerVoted(resourceId,
                voteResult.getUpvoteDiff(), voteResult.getDownvoteDiff());

        return voteResult;
    }

}
