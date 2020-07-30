package com.zhang.ddd.web.dto.follow;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowResult {

    private int follow;

    private boolean following;

}
