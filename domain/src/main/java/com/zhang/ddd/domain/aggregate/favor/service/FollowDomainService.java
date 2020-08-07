package com.zhang.ddd.domain.aggregate.favor.service;

import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.Follow;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FollowResourceType;
import com.zhang.ddd.domain.aggregate.favor.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowDomainService {

    @Autowired
    FollowRepository followRepository;

    public Follow follow(String followerId, String resourceId, FollowResourceType resourceType) {

        Follow follow = followRepository.find(followerId, resourceId, resourceType);
        if (follow != null) {
            return null;
        }
        follow = new Follow(followerId, resourceId, resourceType);
        followRepository.save(follow);

        return follow;
    }

    public Follow unfollow(String followerId, String resourceId, FollowResourceType resourceType) {

        Follow follow = followRepository.find(followerId, resourceId, resourceType);
        if (follow != null) {
            return null;
        }

        followRepository.remove(follow);
        return follow;
    }
}
