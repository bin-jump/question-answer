package com.zhang.ddd.presentation.web.controller;

import java.util.List;

import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteType;
import com.zhang.ddd.infrastructure.common.api.Response;
import com.zhang.ddd.presentation.facade.PostServiceFacade;
import com.zhang.ddd.presentation.facade.dto.post.CommentDto;
import com.zhang.ddd.presentation.facade.dto.follow.FollowResult;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.post.QuestionDto;
import com.zhang.ddd.presentation.facade.dto.post.TagDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import com.zhang.ddd.presentation.facade.dto.vote.VoteRequest;
import com.zhang.ddd.presentation.facade.dto.vote.VoteResult;
import com.zhang.ddd.presentation.web.security.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    PostServiceFacade postServiceFacade;

    @GetMapping
    public Response getQuestions(@RequestParam(required = false) String after,
                                 @RequestParam(defaultValue = "10") int size)  {

        List<QuestionDto> questionDtos = postServiceFacade.getQuestions(after, size);
        String next = questionDtos.size() > 0 ? questionDtos.get(questionDtos.size() - 1).getId() : null;

        return Response.okPagingAfter(questionDtos, next, size);
    }

    @PostMapping
    public Response addQuestion(@RequestBody QuestionDto questionDto) {
        UserDto currentUser = LoginUtil.getCurrentUser();
        QuestionDto res = postServiceFacade.createQuestion(questionDto.getTitle(), questionDto.getBody(),
                currentUser.getId(),
                questionDto.getTags().stream().map(TagDto::getLabel).collect(Collectors.toList()));
        res.setAuthor(currentUser);

        return Response.ok(res);
    }

    @GetMapping("{id}/comment")
    public Response questionComments(@PathVariable String id,
                                     @RequestParam(required = false) String after,
                                     @RequestParam(defaultValue = "10") int size) {

        List<CommentDto> comments = postServiceFacade.getQuestionComments(id, after, size);
        String next = comments.size() > 0 ? comments.get(comments.size() - 1).getId() : null;
        return Response.okPagingAfter(comments, next, size);
    }

    @PostMapping("{id}/comment")
    public Response addComment(@PathVariable String id, @RequestBody CommentDto comment) {

        UserDto currentUser = LoginUtil.getCurrentUser();
        CommentDto commentDto = postServiceFacade.addQuestionComment(currentUser.getId(), id, comment.getBody(), currentUser);
        return Response.ok(commentDto);
    }

    @GetMapping("{id}")
    public Response getQuestion(@PathVariable String id) {

        QuestionDto question = postServiceFacade.getQuestion(id);
        return Response.ok(question);
    }


    @GetMapping("/{id}/answer")
    public Response getAnswer(@PathVariable String id,
                              @RequestParam(required = false) String after,
                              @RequestParam(defaultValue = "10") int size){

        List<AnswerDto> ans = postServiceFacade.getQuestionAnswers(id, after, size);
        String next = ans.size() > 0 ? ans.get(ans.size() - 1).getId() : null;

        return Response.okPagingAfter(ans, next, size);
    }

    @PostMapping("/{id}/answer")
    public Response addAnser(@PathVariable String id, @RequestBody AnswerDto answer){
        UserDto currentUser = LoginUtil.getCurrentUser();
        AnswerDto ans = postServiceFacade.createAnswer(id,
                answer.getBody(), currentUser.getId(), currentUser);

        return Response.ok(ans);
    }

    @PostMapping("/{id}/vote")
    public Response addVote(@PathVariable String id, @Valid @RequestBody VoteRequest request) {
        VoteResult res = VoteResult.builder().vote(1).voteupCount(8).voteType(VoteType.UPVOTE.name()).build();
        return Response.ok(res);
    }


    @DeleteMapping("/{id}/vote")
    public Response removeVote(@PathVariable String id) {
        VoteResult res = VoteResult.builder().vote(-1).voteupCount(7).voteType(VoteType.UPVOTE.name()).build();
        return Response.ok(res);
    }

    @PostMapping("/{id}/follow")
    public Response addFollow(@PathVariable String id) {
        FollowResult res = FollowResult.builder().follow(1).build();
        return Response.ok(res);
    }


    @DeleteMapping("/{id}/follow")
    public Response removeFollow(@PathVariable String id) {
        FollowResult res = FollowResult.builder().follow(-1).build();
        return Response.ok(res);
    }


}
