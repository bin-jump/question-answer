package com.zhang.ddd.domain.aggregate.vote.entity.valueobject;

import com.zhang.ddd.domain.exception.InvalidOperationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class VoteResult {

    private VoteResult(boolean prevUpvote, boolean prevDownvote, boolean isUpvote) {
        if (prevUpvote && prevDownvote) {
            throw new InvalidOperationException("Invalid vote result");
        }

        this.prevUpvote = prevUpvote;
        this.prevDownvote = prevDownvote;
        this.isUpvote = isUpvote;

        upvoteDiff = prevUpvote && !isUpvote ? -1 : (!prevUpvote && isUpvote ? 1 : 0);
        downvoteDiff = prevDownvote && isUpvote ? -1 : (!prevDownvote && !isUpvote ? 1 : 0);
    }

    private VoteResult(boolean prevUpvote, boolean prevDownvote) {
        if (prevUpvote && prevDownvote) {
            throw new InvalidOperationException("Invalid vote result");
        }

        this.prevUpvote = prevUpvote;
        this.prevDownvote = prevDownvote;
        this.upvoteDiff = prevUpvote ? -1 : 0;
        this.downvoteDiff = prevDownvote ? -1 : 0;
    }

    public static VoteResult fromVote(boolean prevUpvote, boolean prevDownvote, boolean isUpvote ) {

        return new VoteResult(prevUpvote, prevDownvote, isUpvote);
    }

    public static VoteResult fromUnvote(boolean prevUpvote, boolean prevDownvote) {

        return new VoteResult(prevUpvote, prevDownvote);
    }

    private boolean prevUpvote;

    private boolean prevDownvote;

    private Boolean isUpvote;

    private int upvoteDiff;

    private int downvoteDiff;

}
