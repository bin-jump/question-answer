package com.zhang.ddd.application.service;

import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.Follow;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FollowResourceType;
import com.zhang.ddd.domain.aggregate.favor.service.FollowDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavorApplicationService {

    @Autowired
    FollowDomainService followDomainService;

    public Follow followUser(String followerId, String userId) {

        Follow follow = followDomainService.follow(followerId, userId, FollowResourceType.USER);
        return follow;
    }

    public Follow followQuestion(String followerId, String questionId) {

        Follow follow = followDomainService.follow(followerId, questionId, FollowResourceType.QUESTION);
        return follow;
    }

}
