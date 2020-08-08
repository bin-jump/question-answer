package com.zhang.ddd.infrastructure.persistence.po;

import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FollowResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowPO {

    private long followerId;

    private long resourceId;

    private FollowResourceType resourceType;

    private Date created;

}
