package com.zhang.ddd.domain.aggregate.vote.entity.valueobject;

import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteResourceType;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteType;
import com.zhang.ddd.domain.shared.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Vote {

    public Vote(String voterId, String resourceId, VoteType voteType, VoteResourceType resourceType) {
        this.voterId = voterId;
        this.resourceId = resourceId;
        this.voteType = voteType;
        this.resourceType = resourceType;
    }

    private String voterId;

    private String resourceId;

    private VoteType voteType;

    private VoteResourceType resourceType;

}
