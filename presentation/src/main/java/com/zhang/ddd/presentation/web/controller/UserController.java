package com.zhang.ddd.presentation.web.controller;

import com.zhang.ddd.application.service.UserApplicationService;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.infrastructure.common.api.Response;
import com.zhang.ddd.presentation.facade.UserServiceFacade;
import com.zhang.ddd.presentation.facade.assembler.UserAssembler;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDTO;
import com.zhang.ddd.presentation.facade.dto.post.QuestionDto;
import com.zhang.ddd.presentation.facade.dto.post.TagDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import com.zhang.ddd.presentation.facade.dto.follow.FollowResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserServiceFacade userServiceFacade;

    @PostMapping
    public Response create(@Valid UserDto userDto) {

        userDto = userServiceFacade.create(userDto);
        return Response.ok(userDto);
    }


    @GetMapping("me")
    public Response test(){
        UserDto userDto = UserDto.builder()
        .id("abc123")
        .name("zhang")
                .email("user@example.com")
                .description("I am a user.")
                .created(1429070000)
                .gender("")
        .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F808.jpg?s=400&d=identicon")
                .build();
        return Response.ok(userDto);
    }

    @GetMapping("{id}")
    public Response getUserInfo(@PathVariable String id){

        return test();
    }

    @GetMapping
    public Response findByName(@RequestParam @NotBlank String name){

        UserDto userDto = UserDto.builder()
                .id("abc123"+name)
                .name(name)
                .email("user@example.com")
                .description("I am a user.")
                .created(1429070000)
                .gender("")
                .avatarUrl("https://www.gravatar.com/avatar/47EEE5C74F589F4867DBD57E9CA9F808.jpg?s=400&d=identicon")
                .build();
        return Response.ok(userDto);
    }

    @GetMapping("{id}/questions")
    public Response getQuestion(@PathVariable String id){
        UserDto user = UserDto.builder()
                .id("abc123")
                .name("zhang")
                .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F808.jpg?s=400&d=identicon")
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
        List<QuestionDto> res = new ArrayList<QuestionDto>();
        res.add(q1);
        res.add(q1);
        res.add(q1);
        res.add(q1);
        res.add(q1);
        return Response.okPagingAfter(res, q1.getId(), 5);
    }

    @GetMapping("{id}/answers")
    public Response getAnswers(@PathVariable String id){
        UserDto user = UserDto.builder()
                .id("abc123")
                .name("zhang")
                .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F808.jpg?s=400&d=identicon")
                .build();

        AnswerDTO a1 = AnswerDTO.builder()
                .authorId(user.getId())
                .author(user)
                .body("Get used to how to write simple programs. " +
                        "Get used to how to write simple programs. " +
                        "Get used to how to write simple programs. " +
                        "Get used to how to write simple programs. " +
                        "Get used to how to write simple programs. " +
                        "Get used to how to write simple programs. " +
                        "Get used to how to write simple programs. " +
                        "Get used to how to write simple programs. ")
                .created(1429077131)
                .id("aaa111")
                .build();

        QuestionDto q1 = QuestionDto.builder()
                .authorId(user.getId())
                .author(user)
                .body("author best way to learn")
                .title("What is the best way to learn python")
                .id("qqq111")
                .cover(a1)
                .commentCount(3)
                .answerCount(3)
                .followCount(6)
                .created(1429070000)

                .tag(new TagDto("python"))
                .tag(new TagDto("programming"))
                .build();
        List<QuestionDto> res = new ArrayList<QuestionDto>();
        res.add(q1);
        res.add(q1);
        res.add(q1);
        res.add(q1);
        res.add(q1);
        return Response.okPagingAfter(res, q1.getId(), 5);
    }

    @PostMapping("{id}/follow")
    public Response followUser(@PathVariable String id) {
        FollowResult res = FollowResult.builder().follow(1).build();
        return Response.ok(res);
    }

    @DeleteMapping("{id}/follow")
    public Response unfollowUser(@PathVariable String id) {
        FollowResult res = FollowResult.builder().follow(-1).build();
        return Response.ok(res);
    }

    @GetMapping("{id}/follow")
    public Response getFollowQuestion(@PathVariable String id) {
        return getQuestion(id);
    }

    @GetMapping("{id}/follower")
    public Response getFollower(@PathVariable String id) {
        UserDto user = UserDto.builder()
                .id("abc123")
                .name("zhang")
                .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F808.jpg?s=400&d=identicon")
                .description("I am as user.")
                .build();
        List<UserDto> res = new ArrayList<>();
        res.add(user);
        res.add(user);
        res.add(user);
        res.add(user);
        res.add(user);
        return Response.okPagingAfter(res, user.getId(), 5);
    }

    @GetMapping("{id}/followee")
    public Response getFollowee(@PathVariable String id) {
        return getFollower(id);
    }

}
