package com.zhang.ddd.presentation.facade;

import com.zhang.ddd.application.service.FavorApplicationService;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.Follow;
import com.zhang.ddd.presentation.facade.dto.follow.FollowResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavorServiceFacade {

    @Autowired
    FavorApplicationService favorApplicationService;

    public FollowResultDto followQuestion(String followerId, String questionId) {

        Follow follow = favorApplicationService.followQuestion(followerId, questionId);
        FollowResultDto resultDto = FollowResultDto.builder()
                .follow(follow != null ? 1 : 0)
                .following(true)
                .build();
        return resultDto;
    }

    public FollowResultDto unfollowQuestion(String followerId, String questionId) {

        Follow follow = favorApplicationService.unfollowQuestion(followerId, questionId);
        FollowResultDto resultDto = FollowResultDto.builder()
                .follow(follow != null ? -1 : 0)
                .following(true)
                .build();
        return resultDto;
    }

    public FollowResultDto followUser(String followerId, String followeeId) {

        Follow follow = favorApplicationService.followUser(followerId, followeeId);
        FollowResultDto resultDto = FollowResultDto.builder()
                .follow(follow != null ? 1 : 0)
                .following(true)
                .build();
        return resultDto;
    }

    public FollowResultDto unfollowUser(String followerId, String followeeId) {

        Follow follow = favorApplicationService.unfollowUser(followerId, followeeId);
        FollowResultDto resultDto = FollowResultDto.builder()
                .follow(follow != null ? -1 : 0)
                .following(true)
                .build();
        return resultDto;
    }

}
