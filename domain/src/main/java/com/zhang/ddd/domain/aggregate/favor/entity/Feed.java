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

    public Feed(Long id, FeedType feedType, FeedAction feedAction, Long resourceId, Long creatorId) {

        if (feedAction == null) {
            throw new InvalidValueException("FeedAction can not be empty.");
        }
        if (feedType == null) {
            throw new InvalidValueException("FeedAType can not be empty.");
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

    private Long resourceId;

    private Long creatorId;

    private Date created;
}
