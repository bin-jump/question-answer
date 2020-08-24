package com.zhang.ddd.application.service;

import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.Follow;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FollowResourceType;
import com.zhang.ddd.domain.aggregate.favor.service.FeedDomainService;
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

    @Autowired
    FeedDomainService feedDomainService;


    public Follow followUser(Long followerId, Long followeeId) {

        Follow follow = followDomainService.follow(followerId, followeeId, FollowResourceType.USER);
        if (follow != null) {
            userDomainService.userFollowed(followerId, followeeId, true);

            feedDomainService.userFollow(followerId, followeeId);
        }
        return follow;
    }

    public Follow unfollowUser(Long followerId, Long followeeId) {

        Follow follow = followDomainService.unfollow(followerId, followeeId, FollowResourceType.USER);
        if (follow != null) {
            userDomainService.userFollowed(followerId, followeeId, false);

            feedDomainService.userUnfollow(followerId, followeeId);
        }

        return follow;
    }

    public Follow followQuestion(Long followerId, Long questionId) {

        Follow follow = followDomainService.follow(followerId, questionId, FollowResourceType.QUESTION);
        if (follow != null) {
            questionDomainService.questionFollow(questionId, true);
            userDomainService.questionFollowed(followerId, true);

            feedDomainService.questionFollow(followerId, questionId);
        }
        return follow;
    }

    public Follow unfollowQuestion(Long followerId, Long questionId) {

        Follow follow = followDomainService.unfollow(followerId, questionId, FollowResourceType.QUESTION);
        if (follow != null) {
            questionDomainService.questionFollow(questionId, false);
            userDomainService.questionFollowed(followerId, false);

            feedDomainService.questionUnfollow(followerId, questionId);
        }

        return follow;
    }

}
