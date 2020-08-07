package com.zhang.ddd.application.service;

import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.Follow;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FollowResourceType;
import com.zhang.ddd.domain.aggregate.favor.service.FollowDomainService;
import com.zhang.ddd.domain.aggregate.post.repository.QuestionRepository;
import com.zhang.ddd.domain.aggregate.post.service.QuestionDomainService;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.domain.aggregate.user.service.UserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavorApplicationService {

    @Autowired
    FollowDomainService followDomainService;

    @Autowired
    QuestionDomainService questionDomainService;

    @Autowired
    UserDomainService userDomainService;

    public Follow followUser(String followerId, String followeeId) {

        Follow follow = followDomainService.follow(followerId, followeeId, FollowResourceType.USER);
        if (follow != null) {
            userDomainService.userFollowed(followerId, followeeId, true);
        }
        return follow;
    }

    public Follow unfollowUser(String followerId, String followeeId) {

        Follow follow = followDomainService.unfollow(followerId, followeeId, FollowResourceType.USER);
        if (follow != null) {
            userDomainService.userFollowed(followerId, followeeId, false);
        }

        return follow;
    }

    public Follow followQuestion(String followerId, String questionId) {

        Follow follow = followDomainService.follow(followerId, questionId, FollowResourceType.QUESTION);
        if (follow != null) {
            questionDomainService.questionFollow(questionId, true);
        }
        return follow;
    }

    public Follow unfollowQuestion(String followerId, String questionId) {

        Follow follow = followDomainService.unfollow(followerId, questionId, FollowResourceType.QUESTION);
        if (follow != null) {
            questionDomainService.questionFollow(questionId, false);
        }

        return follow;
    }

}
