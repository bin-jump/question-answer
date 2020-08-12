package com.zhang.ddd.presentation.web.controller;

import com.zhang.ddd.infrastructure.common.api.Response;
import com.zhang.ddd.presentation.facade.FavorServiceFacade;
import com.zhang.ddd.presentation.facade.UserServiceFacade;
import com.zhang.ddd.presentation.facade.assembler.UserAssembler;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.post.QuestionDto;
import com.zhang.ddd.presentation.facade.dto.post.TagDto;
import com.zhang.ddd.presentation.facade.dto.user.ChangePasswordRequest;
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

        UserDto userDto = LoginUtil.getCurrentUser();
        if (userDto == null) {
            return Response.ok(null);
        }
        userDto = userServiceFacade.findById(userDto.getId());
        return Response.ok(userDto);
    }

    @PutMapping("{id}")
    public Response edit(@PathVariable String id,
                         @RequestBody @Valid UserDto userDto) {
        userDto.setId(id);
        userDto = userServiceFacade.edit(userDto);
        return Response.ok(userDto, "User updated.");
    }

    @PutMapping("{id}/password")
    public Response changePassword(@PathVariable String id,
                         @RequestBody @Valid ChangePasswordRequest request) {
        userServiceFacade
                .changePassword(id, request.getOldPassword(), request.getNewPassword());
        return Response.ok("Password changed.");
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
    public Response getAnswers(@PathVariable String id,
                               @RequestParam(required = false) String after,
                               @RequestParam(defaultValue = "10") int size){

        List<QuestionDto> res = userServiceFacade.findUserAnswers(id, after, size);
        String next = res.size() > 0 ? res.get(res.size() - 1).getId() : null;

        return Response.okPagingAfter(res, next, size);
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
    public Response getFollowQuestion(@PathVariable String id,
                                      @RequestParam(required = false) String after,
                                      @RequestParam(defaultValue = "10") int size) {
        List<QuestionDto> res = userServiceFacade.findfollowedQuestions(id, after, size);
        String next = res.size() > 0 ? res.get(res.size() - 1).getId() : null;

        return  Response.okPagingAfter(res, next, size);
    }

    @GetMapping("{id}/follower")
    public Response getFollower(@PathVariable String id,
                                @RequestParam(required = false) String after,
                                @RequestParam(defaultValue = "10") int size) {
        List<UserDto> res = userServiceFacade.findfollower(id, after, size);
        String next = res.size() > 0 ? res.get(res.size() - 1).getId() : null;

        return  Response.okPagingAfter(res, next, size);
    }

    @GetMapping("{id}/followee")
    public Response getFollowee(@PathVariable String id,
                                @RequestParam(required = false) String after,
                                @RequestParam(defaultValue = "10") int size) {

        List<UserDto> res = userServiceFacade.findfollowee(id, after, size);
        String next = res.size() > 0 ? res.get(res.size() - 1).getId() : null;

        return  Response.okPagingAfter(res, next, size);
    }

}
