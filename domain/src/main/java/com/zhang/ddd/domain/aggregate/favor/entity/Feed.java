package com.zhang.ddd.domain.aggregate.favor.entity;

import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedAction;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedType;
import com.zhang.ddd.domain.exception.InvalidValueException;
import com.zhang.ddd.domain.shared.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Date;

// TODO: write correct setters
@Getter
@Setter
@NoArgsConstructor
public class Feed extends Entity<Feed> {

    public Feed(String id, FeedType feedType, FeedAction feedAction, String resourceId, String creatorId) {
        if (!StringUtils.hasText(id)) {
            throw new InvalidValueException("Feed id can not be empty.");
        }
        if (feedAction == null) {
            throw new InvalidValueException("FeedAction can not be empty.");
        }
        if (feedType == null) {
            throw new InvalidValueException("FeedAType can not be empty.");
        }
        if (!StringUtils.hasText(resourceId)) {
            throw new InvalidValueException("ResourceId id can not be empty.");
        }
        if (!StringUtils.hasText(creatorId)) {
            throw new InvalidValueException("CreatorId id can not be empty.");
        }
        this.id = id;
        this.feedType = feedType;
        this.feedAction = feedAction;
        this.resourceId = resourceId;
        this.creatorId = creatorId;

        this.created = new Date();
    }

    private FeedType feedType;

    private FeedAction feedAction;

    private String resourceId;

    private String creatorId;

    private Date created;
}
