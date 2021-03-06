package com.zhang.ddd.presentation.facade.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedDto {

    private Long id;

    private String feedReason;

    private String feedType;

    private Long creatorId;

    private Object target;

    private long created;

}
