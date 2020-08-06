package com.zhang.ddd.domain.aggregate.vote.service;

import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.*;
import com.zhang.ddd.domain.aggregate.vote.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteDomainService {

    @Autowired
    VoteRepository voteRepository;


    // return current vote
    public VoteResult vote(String voterId, String resourceId,
                           VoteResourceType resourceType, VoteType voteType) {
        Vote vote = voteRepository.find(voterId, resourceId, resourceType);
        boolean currentUpvote = false, currentDownvote = false;
        if (vote != null) {
            currentUpvote = vote.getVoteType() == VoteType.UPVOTE ? true : false;
            currentDownvote = vote.getVoteType() == VoteType.DOWNVOTE ? true : false;
        }

        if (vote == null) {
            vote = new Vote(voterId, resourceId, voteType, resourceType);
            voteRepository.save(vote);
        } else if (vote.getVoteType() != voteType) {
            Vote voteToUpdate =new Vote(voterId, resourceId, voteType, resourceType);
            voteRepository.update(voteToUpdate);
        }

        return VoteResult.fromVote(currentUpvote, currentDownvote, voteType == VoteType.UPVOTE);
    }

    // return current vote
    public VoteResult unvote(String voterId, String resourceId, VoteResourceType resourceType) {
        Vote vote = voteRepository.find(voterId, resourceId, resourceType);
        voteRepository.remove(vote);

        if (vote == null) {
            return VoteResult.fromUnvote(false, false);
        }

        return VoteResult.fromUnvote(vote.getVoteType() == VoteType.UPVOTE,
                vote.getVoteType() == VoteType.DOWNVOTE);
    }
}
