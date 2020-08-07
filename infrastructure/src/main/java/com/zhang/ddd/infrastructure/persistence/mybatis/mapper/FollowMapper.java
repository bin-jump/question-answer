package com.zhang.ddd.infrastructure.persistence.mybatis.mapper;

import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FollowResourceType;
import com.zhang.ddd.infrastructure.persistence.po.FollowPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper {

    void insert(FollowPO followPO);

    void delete(FollowPO followPO);

    FollowPO find(String followerId, String resourceId, FollowResourceType resourceType);

    List<FollowPO> findByResourceIds(String followerId, List<String> resourceIds,
                                     FollowResourceType resourceType);


    List<FollowPO> findFollowed(String followerId, FollowResourceType resourceType,
                                         Long cursor, int size);

    List<FollowPO> findResourceFolloweeIds(String resourceId, FollowResourceType resourceType,
                                           Long cursor, int size);

}
