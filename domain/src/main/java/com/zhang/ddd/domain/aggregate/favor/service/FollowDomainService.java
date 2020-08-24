package com.zhang.ddd.domain.aggregate.favor.service;

import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.Follow;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FollowResourceType;
import com.zhang.ddd.domain.aggregate.favor.repository.FollowRepository;
import com.zhang.ddd.domain.exception.InvalidOperationException;
import com.zhang.ddd.domain.shared.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowDomainService {

    @Autowired
    SequenceRepository sequenceRepository;

    @Autowired
    FollowRepository followRepository;

    public Follow follow(Long followerId, Long resourceId, FollowResourceType resourceType) {
        if (followerId == resourceId) {
            throw new InvalidOperationException("Wrong follow operation.");
        }

        Follow follow = followRepository.find(followerId, resourceId, resourceType);
        if (follow != null) {
            return null;
        }

        long id = sequenceRepository.nextId();
        follow = new Follow(id, followerId, resourceId, resourceType);
        followRepository.save(follow);

        return follow;
    }

    public Follow unfollow(Long followerId, Long resourceId, FollowResourceType resourceType) {

        Follow follow = followRepository.find(followerId, resourceId, resourceType);
        if (follow == null) {
            return null;
        }

        followRepository.remove(follow);
        return follow;
    }
}
