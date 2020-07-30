package com.zhang.ddd.web.dto.vote;

import com.zhang.ddd.domain.vote.valueobject.VoteType;
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
