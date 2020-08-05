package com.zhang.ddd.presentation.web.controller;

import java.util.List;
import com.zhang.ddd.infrastructure.common.api.Response;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.post.FeedDto;
import com.zhang.ddd.presentation.facade.dto.post.QuestionDto;
import com.zhang.ddd.presentation.facade.dto.post.TagDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Slf4j
@RequestMapping("/api/feed")
public class FeedController {

    @GetMapping
    public Response getFeed(@RequestParam(value = "after", required = false) String after) {

        UserDto user = UserDto.builder()
                .id("uuu111")
                .name("zhang")
                .description("I am a user.")
                .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F808.jpg?s=400&d=identicon")
                .build();

        AnswerDto a = AnswerDto.builder()
                .authorId(user.getId())
                .author(user)
                .body("Single answer request. " +
                        "Single answer request. " +
                        "Get used to how to write simple programs. " +
                        "Get used to how to write simple programs. " +
                        "Get used to how to write simple programs. " +
                        "Get used to how to write simple programs. " +
                        "Get used to how to write simple programs. " +
                        "Get used to how to write simple programs. ")
                .created(1429077131)
                .title("What is the best way to learn python")
                .id("aaa111")
                .build();

        QuestionDto q1 = QuestionDto.builder()
                .authorId(user.getId())
                .author(user)
                .body("author best way to learn")
                .title("What is the best way to learn python")
                .id("qqq111")
                .commentCount(3)
                .answerCount(3)
                .followCount(6)
                .created(1429070000)
                .tag(new TagDto("python"))
                .tag(new TagDto("programming"))
                .build();

        QuestionDto q2 = QuestionDto.builder()
                .authorId(user.getId())
                .author(user)
                .body("author best way to learn")
                .title("What is the best way to learn python")
                .id("qqq111")
                .cover(a)
                .commentCount(3)
                .answerCount(3)
                .followCount(6)
                .created(1429070000)
                .tag(new TagDto("python"))
                .tag(new TagDto("programming"))
                .build();

        List<FeedDto> res = new ArrayList<>();
        FeedDto f1 = FeedDto.builder()
                .id("fff111")
                .feedReason("User1 followed")
                .feedType("USER")
                .target(user)
                .created(1429077131)
                .build();

        FeedDto f2 = FeedDto.builder()
                .id("fff222")
                .feedReason("User2 answerd")
                .feedType("ANSWER")
                .target(q2)
                .created(1429077131)
                .build();

        FeedDto f3 = FeedDto.builder()
                .id("fff333")
                .feedReason("User3 asked")
                .feedType("QUESTION")
                .target(q1)
                .created(1429077131)
                .build();

        res.add(f1);
        res.add(f2);
        res.add(f3);

        return Response.okPagingAfter(res, f3.getId(), 3);

    }

}
