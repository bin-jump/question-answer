package com.zhang.ddd.presentation.facade.dto.vote;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteResultDto {

    private String voteType;

    private int vote;

    private int upvoteDiff;

    private int downvoteDiff;

    private int voteupCount;


}
