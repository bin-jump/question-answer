package com.zhang.ddd.presentation.web.controller;

import com.zhang.ddd.infrastructure.common.api.Response;
import com.zhang.ddd.presentation.facade.FavorServiceFacade;
import com.zhang.ddd.presentation.facade.UserServiceFacade;
import com.zhang.ddd.presentation.facade.assembler.UserAssembler;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.post.QuestionDto;
import com.zhang.ddd.presentation.facade.dto.post.TagDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import com.zhang.ddd.presentation.facade.dto.follow.FollowResultDto;
import com.zhang.ddd.presentation.web.security.LoginUtil;
import com.zhang.ddd.presentation.web.security.WebUserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    FavorServiceFacade favorServiceFacade;

    @PostMapping
    public Response create(@RequestBody @Valid  UserDto userDto) {

        userDto = userServiceFacade.create(userDto);
        return Response.ok(userDto);
    }

    @GetMapping("me")
    public Response me() {

        UserDto userDto = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof WebUserPrincipal) {
            userDto = UserAssembler.toDTO(((WebUserPrincipal)principal).getUser());
        }
        return Response.ok(userDto);
    }

    @GetMapping("{id}")
    public Response getUserInfo(@PathVariable String id){

        UserDto userDto = userServiceFacade.findById(id);
        return Response.ok(userDto);
    }

    @GetMapping
    public Response findByName(@RequestParam @NotBlank String name){

        UserDto userDto = userServiceFacade.findByName(name);
        return Response.ok(userDto);
    }

    @GetMapping("{id}/questions")
    public Response getQuestion(@PathVariable String id,
                                @RequestParam(required = false) String after,
                                @RequestParam(defaultValue = "10") int size){

        List<QuestionDto> res = userServiceFacade.findUserQuestions(id, after, size);
        String next = res.size() > 0 ? res.get(res.size() - 1).getId() : null;

        return  Response.okPagingAfter(res, next, size);
    }

    @GetMapping("{id}/answers")
    public Response getAnswers(@PathVariable String id){
        UserDto user = UserDto.builder()
                .id("abc123")
                .name("zhang")
                .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F808.jpg?s=400&d=identicon")
                .build();

        AnswerDto a1 = AnswerDto.builder()
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
        UserDto currentUser = LoginUtil.getCurrentUser();
        FollowResultDto res = favorServiceFacade.followUser(currentUser.getId(), id);
        return Response.ok(res);
    }

    @DeleteMapping("{id}/follow")
    public Response unfollowUser(@PathVariable String id) {
        UserDto currentUser = LoginUtil.getCurrentUser();
        FollowResultDto res = favorServiceFacade.unfollowUser(currentUser.getId(), id);
        return Response.ok(res);
    }

    @GetMapping("{id}/follow")
    public Response getFollowQuestion(@PathVariable String id) {
        return getQuestion(id, null, 3);
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
