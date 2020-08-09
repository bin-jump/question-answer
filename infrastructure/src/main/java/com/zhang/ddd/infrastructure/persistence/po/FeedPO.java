package com.zhang.ddd.infrastructure.persistence.po;

import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedAction;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedPO {

    private long id;

    private long version;

    private FeedType feedType;

    private FeedAction feedAction;

    private long resourceId;

    private long creatorId;

    private Date created;
}
