package com.zhang.ddd.presentation.facade.dto.follow;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowResultDto {

    private int follow;

    private boolean following;

}
