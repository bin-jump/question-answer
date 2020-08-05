package com.zhang.ddd.presentation.web.controller;

import java.util.List;

import com.zhang.ddd.domain.aggregate.vote.valueobject.VoteType;
import com.zhang.ddd.infrastructure.common.api.PagingData;
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
import java.util.ArrayList;
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

        return Response.okPagingAfter(questionDtos, questionDtos.get(questionDtos.size() - 1).getId(), size);
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
    public Response questionComments() {
        UserDto user = UserDto.builder()
                .id("abc123")
                .name("zhang")
                .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F808.jpg?s=400&d=identicon")
                .build();
        List<CommentDto> comments = new ArrayList<>();
        CommentDto comment = CommentDto.builder()
                .id("ccc111")
                .resourceId("qqq111")
                .resourceType("QUESTION")
                .authorId(user.getId())
                .author(user)
                .body("This is a comment. This is a comment. This is a comment. " +
                        "This is a comment. This is a comment. This is a comment. ")
                .created(1429070000)
                .build();
        comments.add(comment);
        comments.add(comment);
        comments.add(comment);
        return Response.okPagingAfter(comments, comment.getId(), 3);
    }

    @PostMapping("{id}/comment")
    public Response addComment(@PathVariable String id, @RequestBody CommentDto comment) {

        CommentDto res = (CommentDto) ((PagingData)(questionComments().getData())).getChildren().get(0);
        res.setBody(comment.getBody());
        res.setResourceId(comment.getResourceType());

        return Response.ok(res);
    }

    @GetMapping("{id}")
    public Response question() throws InterruptedException {
        QuestionDto q = (QuestionDto)((PagingData)(getQuestions(null, 10).getData())).getChildren().get(0);
        q.setFollowing(true);
        return Response.ok(q);
    }


    @GetMapping("/{id}/answer")
    public Response getAnswer(){
        UserDto user = UserDto.builder()
                .id("abc123")
                .name("zhang")
                .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F808.jpg?s=400&d=identicon")
                .build();

        List<AnswerDto> res = new ArrayList<AnswerDto>();

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
                .commentCount(6)
                .id("aaa111")
                .build();

        AnswerDto a2 = AnswerDto.builder()
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
                .commentCount(9)
                .id("aaa222")
                .build();


        res.add(a1);
        res.add(a2);

        return Response.okPagingAfter(res, a1.getId(), 2);
    }

    @PostMapping("/{id}/answer")
    public Response addAnser(@PathVariable String id, @RequestBody AnswerDto answer){
        AnswerDto ans = (AnswerDto) ((PagingData)(getAnswer().getData())).getChildren().get(0);
        ans.setBody(answer.getBody());

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
