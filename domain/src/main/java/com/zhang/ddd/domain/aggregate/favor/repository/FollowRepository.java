package com.zhang.ddd.domain.aggregate.favor.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.Follow;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FollowResourceType;

public interface FollowRepository {

    void save(Follow follow);

    Follow find(Long followerId, Long resourceId, FollowResourceType resourceType);

    List<Follow> findByResourceIds(Long followerId, List<Long> resourceIds,
                                   FollowResourceType resourceType);

    void remove(Follow follow);

    List<Follow> findFollowed(Long followId,
                                         FollowResourceType resourceType, FavorPaging paging);

    List<Follow> findFollower(Long resourceId,
                                         FollowResourceType resourceType, FavorPaging paging);

}
