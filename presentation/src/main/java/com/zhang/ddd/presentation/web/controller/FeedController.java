package com.zhang.ddd.presentation.web.controller;

import java.util.List;
import com.zhang.ddd.infrastructure.common.api.Response;
import com.zhang.ddd.presentation.facade.FeedServiceFacade;
import com.zhang.ddd.presentation.facade.dto.post.FeedDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import com.zhang.ddd.presentation.web.security.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/feed")
public class FeedController {


    @Autowired
    FeedServiceFacade feedServiceFacade;

    @GetMapping
    public Response getFeed(@RequestParam(value = "after", required = false) Long after,
                            @RequestParam(defaultValue = "10") int size) {

        UserDto currentUser = LoginUtil.getCurrentUser();
        List<FeedDto> feeds = feedServiceFacade.findFeed(currentUser.getId(), after, size);
        Long next = feeds.size() > 0 ? feeds.get(feeds.size() - 1).getId() : null;

        return Response.okPagingAfter(feeds, next, size);

    }

}
