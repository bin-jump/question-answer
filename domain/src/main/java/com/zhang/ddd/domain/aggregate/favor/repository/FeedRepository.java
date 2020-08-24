package com.zhang.ddd.domain.aggregate.favor.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.favor.entity.Feed;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedAction;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedType;

public interface FeedRepository {

    Long nextId();

    void save(Feed feed);

    void remove(Feed feed);

    Feed find(Long creatorId, Long resourceId, FeedType feedType, FeedAction feedAction);

    List<Feed> getUserFeed(Long userId, FavorPaging paging);
}
