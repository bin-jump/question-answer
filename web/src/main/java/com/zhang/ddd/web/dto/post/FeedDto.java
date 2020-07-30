package com.zhang.ddd.web.dto.post;

import com.zhang.ddd.web.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedDto {

    private String id;

    private String feedReason;

    private String feedType;

    private Object target;

    private long created;

}
