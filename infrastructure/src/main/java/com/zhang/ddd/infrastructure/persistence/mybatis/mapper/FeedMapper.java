package com.zhang.ddd.infrastructure.persistence.mybatis.mapper;

import java.util.List;

import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedAction;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedType;
import com.zhang.ddd.infrastructure.persistence.po.FeedPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedMapper {

    int insert(FeedPO feedPO);

    int delete(FeedPO feedPO);

    List<FeedPO> findUseFeed(long userId, Long cursor, int size);

    FeedPO find(long creatorId, long resourceId, FeedType feedType, FeedAction feedAction);
}
