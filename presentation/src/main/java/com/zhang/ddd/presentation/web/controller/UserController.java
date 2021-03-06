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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
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

    @PutMapping
    public Response edit(@RequestBody @Valid UserDto userDto) {
        userDto.setId(LoginUtil.getCurrentUser().getId());
        userDto = userServiceFacade.edit(userDto);
        return Response.ok(userDto, "User updated.");
    }

    @PutMapping("password")
    public Response changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        userServiceFacade
                .changePassword(LoginUtil.getCurrentUser().getId(),
                        request.getOldPassword(), request.getNewPassword());
        return Response.ok(null, "Password changed.");
    }

    @PostMapping("avatar")
    public Response changeAvatarImage(@RequestParam("image") MultipartFile image) throws IOException {

        userServiceFacade.changeAvatarImage(LoginUtil.getCurrentUser().getId(), image);
        return Response.ok(null, "User image changed.");
    }

    @GetMapping("{id}")
    public Response getUserInfo(@PathVariable Long id){

        UserDto userDto = userServiceFacade.findById(id);
        return Response.ok(userDto);
    }

    @GetMapping
    public Response findByName(@RequestParam @NotBlank String name){

        UserDto userDto = userServiceFacade.findByName(name);
        return Response.ok(userDto);
    }

    @GetMapping("{id}/questions")
    public Response getQuestion(@PathVariable Long id,
                                @RequestParam(required = false) Long after,
                                @RequestParam(defaultValue = "10") int size){

        List<QuestionDto> res = userServiceFacade.findUserQuestions(id, after, size);
        Long next = res.size() > 0 ? res.get(res.size() - 1).getId() : null;

        return  Response.okPagingAfter(res, next, size);
    }

    @GetMapping("{id}/answers")
    public Response getAnswers(@PathVariable Long id,
                               @RequestParam(required = false) Long after,
                               @RequestParam(defaultValue = "10") int size){

        List<QuestionDto> res = userServiceFacade.findUserAnswers(id, after, size);
        Long next = res.size() > 0 ? res.get(res.size() - 1).getId() : null;

        return Response.okPagingAfter(res, next, size);
    }

    @PostMapping("{id}/follow")
    public Response followUser(@PathVariable Long id) {
        UserDto currentUser = LoginUtil.getCurrentUser();
        FollowResultDto res = favorServiceFacade.followUser(currentUser.getId(), id);
        return Response.ok(res);
    }

    @DeleteMapping("{id}/follow")
    public Response unfollowUser(@PathVariable Long id) {
        UserDto currentUser = LoginUtil.getCurrentUser();
        FollowResultDto res = favorServiceFacade.unfollowUser(currentUser.getId(), id);
        return Response.ok(res);
    }

    @GetMapping("{id}/follow")
    public Response getFollowQuestion(@PathVariable Long id,
                                      @RequestParam(required = false) Long after,
                                      @RequestParam(defaultValue = "10") int size) {
        List<QuestionDto> res = userServiceFacade.findfollowedQuestions(id, after, size);
        Long next = res.size() > 0 ? res.get(res.size() - 1).getId() : null;

        return  Response.okPagingAfter(res, next, size);
    }

    @GetMapping("{id}/follower")
    public Response getFollower(@PathVariable Long id,
                                @RequestParam(required = false) Long after,
                                @RequestParam(defaultValue = "10") int size) {
        List<UserDto> res = userServiceFacade.findfollower(id, after, size);
        Long next = res.size() > 0 ? res.get(res.size() - 1).getId() : null;

        return  Response.okPagingAfter(res, next, size);
    }

    @GetMapping("{id}/followee")
    public Response getFollowee(@PathVariable Long id,
                                @RequestParam(required = false) Long after,
                                @RequestParam(defaultValue = "10") int size) {

        List<UserDto> res = userServiceFacade.findfollowee(id, after, size);
        Long next = res.size() > 0 ? res.get(res.size() - 1).getId() : null;

        return  Response.okPagingAfter(res, next, size);
    }

}
