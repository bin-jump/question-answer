package com.zhang.ddd.presentation.facade.dto.vote;

import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteType;
import com.zhang.ddd.infrastructure.common.validation.EnumValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteRequest {

    @EnumValid(target = VoteType.class)
    private String voteType;

}
