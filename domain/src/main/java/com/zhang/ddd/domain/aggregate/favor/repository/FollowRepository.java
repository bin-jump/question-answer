package com.zhang.ddd.domain.aggregate.favor.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.Follow;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FollowResourceType;

public interface FollowRepository {

    void save(Follow follow);

    Follow find(String followerId, String resourceId, FollowResourceType resourceType);

    List<Follow> findByResourceIds(String followerId, List<String> resourceIds,
                                   FollowResourceType resourceType);

    void remove(Follow follow);

    List<Follow> findFollowed(String followId,
                                         FollowResourceType resourceType, FavorPaging paging);

    List<Follow> findFollowee(String resourceId,
                                         FollowResourceType resourceType, FavorPaging paging);

}
