package com.zhang.ddd.presentation.facade.assembler;

import com.zhang.ddd.domain.aggregate.favor.entity.Feed;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedAction;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedType;
import com.zhang.ddd.presentation.facade.dto.post.FeedDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;

public class FeedAssembler {

    public static FeedDto toDTO(Feed feed, UserDto userDto) {

        FeedDto feedDto = FeedDto.builder()
                .id(feed.getId())
                .feedReason(makeFeedReason(feed.getFeedType(),
                        feed.getFeedAction(), userDto.getName()))
                .feedType(feed.getFeedType().toString())
                .created(feed.getCreated().getTime())
                .build();

        return feedDto;
    }

    private static String makeFeedReason(FeedType feedType, FeedAction feedAction, String userName) {
        String action = feedAction.name().toLowerCase();
        //action = action.substring(0, 1).toUpperCase() + action.substring(1);
        String resourceName = feedType.name().toLowerCase();
        resourceName = resourceName.substring(0, 1).toUpperCase() + resourceName.substring(1);

        String reason = String.format("%s %s %s", userName, action, resourceName);
        return reason;
    }

}
