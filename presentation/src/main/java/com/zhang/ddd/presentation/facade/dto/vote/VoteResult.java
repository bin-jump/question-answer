package com.zhang.ddd.presentation.facade.dto.vote;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteResult {

    private String voteType;

    private int vote;

    private int voteupCount;


}
