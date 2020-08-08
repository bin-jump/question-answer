package com.zhang.ddd.infrastructure.persistence.po;

import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteResourceType;
import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotePO {

    private long voterId;

    private long resourceId;

    private VoteType voteType;

    private VoteResourceType resourceType;
}
