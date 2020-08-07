package com.zhang.ddd.domain.aggregate.favor.entity.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Follow {

    public Follow(String followerId, String resourceId, FollowResourceType followType) {
        this.followerId = followerId;
        this.resourceId = resourceId;
        this.resourceType = followType;
        this.created = new Date();
    }

    private String followerId;

    private String resourceId;

    private FollowResourceType resourceType;

    private Date created;

}
