package com.zhang.ddd.domain.aggregate.favor.entity.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Follow {

    public Follow(Long id, Long followerId, Long resourceId, FollowResourceType followType) {
        this.id = id;
        this.followerId = followerId;
        this.resourceId = resourceId;
        this.resourceType = followType;
        this.created = new Date();
    }

    private long id;

    private Long followerId;

    private Long resourceId;

    private FollowResourceType resourceType;

    private Date created;

}
