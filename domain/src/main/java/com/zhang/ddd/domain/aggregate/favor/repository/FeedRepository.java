package com.zhang.ddd.domain.aggregate.favor.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.favor.entity.Feed;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedAction;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedType;

public interface FeedRepository {

    String nextId();

    void save(Feed feed);

    void remove(Feed feed);

    Feed find(String creatorId, String resourceId, FeedType feedType, FeedAction feedAction);

    List<Feed> getUserFeed(String userId, FavorPaging paging);
}
