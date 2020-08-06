package com.zhang.ddd.presentation.facade;

import com.zhang.ddd.application.service.VoteApplicationService;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteResult;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteType;
import com.zhang.ddd.presentation.facade.dto.vote.VoteRequest;
import com.zhang.ddd.presentation.facade.dto.vote.VoteResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceFacade {

    @Autowired
    VoteApplicationService voteApplicationService;

    public VoteResultDto voteQuestion(String voterId, String questionId, VoteRequest request) {
        boolean upvote = VoteType.UPVOTE.name().equals(request.getVoteType());
        VoteResult voteResult = voteApplicationService.voteQuestion(voterId, questionId, upvote);

        VoteResultDto res = VoteResultDto.builder()
                .upvoteDiff(voteResult.getUpvoteDiff())
                .downvoteDiff(voteResult.getDownvoteDiff())
                .voteType(request.getVoteType())
                .build();

        return res;
    }

    public VoteResultDto unvoteQuestion(String voterId, String questionId) {
        VoteResult voteResult = voteApplicationService.unvoteQuestion(voterId, questionId);

        VoteResultDto res = VoteResultDto.builder()
                .upvoteDiff(voteResult.getUpvoteDiff())
                .downvoteDiff(voteResult.getDownvoteDiff())
                .build();

        return res;
    }

    public VoteResultDto voteQAnswer(String voterId, String questionId, VoteRequest request) {
        boolean upvote = VoteType.UPVOTE.name().equals(request.getVoteType());
        VoteResult voteResult = voteApplicationService.voteAnswer(voterId, questionId, upvote);

        VoteResultDto res = VoteResultDto.builder()
                .upvoteDiff(voteResult.getUpvoteDiff())
                .downvoteDiff(voteResult.getDownvoteDiff())
                .voteType(request.getVoteType())
                .build();

        return res;
    }

    public VoteResultDto unvoteAnswer(String voterId, String questionId) {
        VoteResult voteResult = voteApplicationService.unvoteAnswer(voterId, questionId);

        VoteResultDto res = VoteResultDto.builder()
                .upvoteDiff(voteResult.getUpvoteDiff())
                .downvoteDiff(voteResult.getDownvoteDiff())
                .build();

        return res;
    }

}
