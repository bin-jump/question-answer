package com.zhang.ddd.presentation.web.controller;

import com.zhang.ddd.infrastructure.common.api.Response;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.post.QuestionDto;
import com.zhang.ddd.presentation.facade.dto.post.SearchDto;
import com.zhang.ddd.presentation.facade.dto.post.TagDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/search")
public class SearchController {

    @GetMapping
    public Response search(@RequestParam(value="q", required=true) String keyWord)  {

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
                .title("What is the best way to learn python")
                .id("qqq111")
                .cover(a1)
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

        SearchDto s1 = SearchDto.builder()
                .id("search111")
                .searchType("ANSWER")
                .question(q2)
                .answer(a1)
                .created(1429070000)
                .build();

        SearchDto s2 = SearchDto.builder()
                .id("search222")
                .searchType("QUESTION")
                .question(q2)
                .created(1429070000)
                .build();

        SearchDto s3 = SearchDto.builder()
                .id("search333")
                .searchType("QUESTION")
                .question(q2)
                .created(1429070000)
                .build();

        List<SearchDto> res = new ArrayList<>();

        res.add(s1);

        res.add(s2);
        res.add(s3);


        return Response.okPagingAfter(res, s1.getId(), 3);
    }
}
