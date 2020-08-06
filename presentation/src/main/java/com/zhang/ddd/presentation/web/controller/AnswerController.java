package com.zhang.ddd.presentation.web.controller;

import com.zhang.ddd.infrastructure.common.api.Response;
import com.zhang.ddd.presentation.facade.PostServiceFacade;
import com.zhang.ddd.presentation.facade.VoteServiceFacade;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import com.zhang.ddd.presentation.facade.dto.post.CommentDto;
import com.zhang.ddd.presentation.facade.dto.vote.VoteRequest;
import com.zhang.ddd.presentation.facade.dto.vote.VoteResultDto;
import com.zhang.ddd.presentation.web.security.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/answer")
public class AnswerController {

    @Autowired
    PostServiceFacade postServiceFacade;

    @Autowired
    VoteServiceFacade voteServiceFacade;

    @GetMapping("{id}")
    public Response getAnswer(@PathVariable String id) {

        AnswerDto answer = postServiceFacade.getAnswer(id);
        return Response.ok(answer);
    }

    @GetMapping("{id}/comment")
    public Response answerComments(@PathVariable String id,
                                   @RequestParam(required = false) String after,
                                   @RequestParam(defaultValue = "10") int size) {

        List<CommentDto> comments = postServiceFacade.getAnswerComments(id, after, size);
        String next = comments.size() > 0 ? comments.get(comments.size() - 1).getId() : null;
        return Response.okPagingAfter(comments, next, size);
    }

    @PostMapping("{id}/comment")
    public Response addComment(@PathVariable String id, @RequestBody CommentDto comment) {

        UserDto currentUser = LoginUtil.getCurrentUser();
        CommentDto commentDto = postServiceFacade.addAnswerComment(currentUser.getId(), id, comment.getBody(), currentUser);

        return Response.ok(commentDto);
    }

    @PostMapping("{id}/vote")
    public Response addVote(@PathVariable String id, @Valid @RequestBody VoteRequest request) {

        UserDto currentUser = LoginUtil.getCurrentUser();
        VoteResultDto res = voteServiceFacade.voteQAnswer(currentUser.getId(), id, request);

        return Response.ok(res);

    }

    @DeleteMapping("{id}/vote")
    public Response removeVote(@PathVariable String id) {

        UserDto currentUser = LoginUtil.getCurrentUser();
        VoteResultDto res = voteServiceFacade.unvoteAnswer(currentUser.getId(), id);

        return Response.ok(res);

    }

}
