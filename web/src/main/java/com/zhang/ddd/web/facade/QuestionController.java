package com.zhang.ddd.web.facade;

import java.util.List;

import com.zhang.ddd.domain.vote.valueobject.VoteType;
import com.zhang.ddd.infrastructure.common.api.PagingData;
import com.zhang.ddd.infrastructure.common.api.Response;
import com.zhang.ddd.web.dto.comment.CommentDto;
import com.zhang.ddd.web.dto.follow.FollowResult;
import com.zhang.ddd.web.dto.post.AnswerDTO;
import com.zhang.ddd.web.dto.post.QuestionDto;
import com.zhang.ddd.web.dto.post.TagDto;
import com.zhang.ddd.web.dto.user.UserDto;
import com.zhang.ddd.web.dto.vote.VoteRequest;
import com.zhang.ddd.web.dto.vote.VoteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@Slf4j
@RequestMapping("/api/question")
public class QuestionController {

    @GetMapping
    public Response getQuestions()  {

        UserDto user = UserDto.builder()
                .id("abc123")
                .name("zhang")
                .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F808.jpg?s=400&d=identicon")
                .build();

        List<QuestionDto> res = new ArrayList<QuestionDto>();
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

        QuestionDto q2 = QuestionDto.builder()
                .authorId(user.getId())
                .author(user)
                .body("authorbest way to learn")
                .title("What is the best way to learn python")
                .id("qqq111")
                .created(1429070000)
                .followCount(7)
                .commentCount(20)
                .tag(new TagDto("python"))
                .tag(new TagDto("programming"))
                .build();

        res.add(q1);

        res.add(q2);
        res.add(q1);


        return Response.okPagingAfter(res, q1.getId(), 3);
    }

    @PostMapping
    public Response addQuestion(@RequestBody QuestionDto questionDto) {
        UserDto user = UserDto.builder()
                .id("abc123")
                .name("zhang")
                .avatarUrl("https://www.gravatar.com/avatar/47BCE5C74F589F4867DBD57E9CA9F808.jpg?s=400&d=identicon")
                .build();
        questionDto.setAuthor(user);
        questionDto.setId("qqq111");
        questionDto.setAuthorId(user.getId());
        questionDto.setCreated(1429070000);
        return Response.ok(questionDto);
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
        QuestionDto q = (QuestionDto)((PagingData)(getQuestions().getData())).getChildren().get(0);
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

        List<AnswerDTO> res = new ArrayList<AnswerDTO>();

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
                .commentCount(6)
                .id("aaa111")
                .build();

        AnswerDTO a2 = AnswerDTO.builder()
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
    public Response addAnser(@PathVariable String id, @RequestBody AnswerDTO answer){
        AnswerDTO ans = (AnswerDTO) ((PagingData)(getAnswer().getData())).getChildren().get(0);
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
