package com.zhang.ddd.presentation.web.controller;

import com.zhang.ddd.infrastructure.common.api.PagingData;
import com.zhang.ddd.infrastructure.common.api.Response;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import com.zhang.ddd.presentation.facade.dto.post.CommentDto;
import com.zhang.ddd.presentation.facade.dto.vote.VoteRequest;
import com.zhang.ddd.presentation.facade.dto.vote.VoteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/answer")
public class AnswerController {

    String vote = "";

    @GetMapping("{id}")
    public Response getAnswer(@RequestParam(value = "questionId", required = false) String questionId) {
        UserDto user = UserDto.builder()
                .id("uuu111")
                .name("zhang")
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
                .id("aaa111")
                .build();

        return Response.ok(a);
    }

    @GetMapping("{id}/comment")
    public Response answerComments(@PathVariable String id) {
        UserDto user = UserDto.builder()
                .id(id)
                .name("zhang")
                .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F808.jpg?s=400&d=identicon")
                .build();
        List<CommentDto> comments = new ArrayList<>()
                ;
        CommentDto comment = CommentDto.builder()
                .id("ccc111")
                .resourceId("qqq111")
                .resourceType("ANSWER")
                .authorId(user.getId())
                .author(user)
                .body("This is a comment. This is a comment. This is a comment. " +
                        "This is a comment. This is a comment. This is a comment. ")
                .created(1429070000)
                .build();


        comments.add(comment);
        comments.add(comment);
        comments.add(comment);
        return Response.okPagingAfter(comments, comment.getId(), 10);
    }

    @PostMapping("{id}/comment")
    public Response addComment(@PathVariable String id, @RequestBody CommentDto comment) {

        CommentDto res = (CommentDto) ((PagingData)(answerComments(id).getData())).getChildren().get(0);
        res.setBody(comment.getBody());
        res.setResourceId(comment.getResourceType());

        return Response.ok(res);
    }

    @PostMapping("{id}/vote")
    public Response addVote(@Valid @RequestBody VoteRequest request) {

        VoteResult res = VoteResult.builder().vote(1).voteupCount(16).build();
        res.setVoteType(request.getVoteType());
        vote = request.getVoteType();
        return Response.ok(res);

    }

    @DeleteMapping("{id}/vote")
    public Response removeVote(@PathVariable String id) {

        VoteResult res = VoteResult.builder().vote(-1).voteupCount(16).build();
        res.setVoteType(vote);
        return Response.ok(res);

    }

}
