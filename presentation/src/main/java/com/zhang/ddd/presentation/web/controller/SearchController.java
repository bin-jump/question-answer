package com.zhang.ddd.presentation.web.controller;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.zhang.ddd.domain.exception.InvalidOperationException;
import com.zhang.ddd.infrastructure.common.api.Response;
import com.zhang.ddd.presentation.facade.SearchServiceFacade;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.post.QuestionDto;
import com.zhang.ddd.presentation.facade.dto.post.SearchDto;
import com.zhang.ddd.presentation.facade.dto.post.TagDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    SearchServiceFacade searchServiceFacade;

    @GetMapping
    public Response searchPost(@RequestParam(value="q") String keyWord,
                               @RequestParam(value="cursor", required = false) String cursor,
                               @RequestParam(value="size", defaultValue = "10") int size) {

        Float cursorScore = null;
        String cursorId = null;
        if (cursor != null) {
            Object[] cursors = toCursors(cursor);
            cursorScore = (Float)cursors[0];
            cursorId = (String)cursors[1];
        }

        List<SearchDto> res = searchServiceFacade.searchPost(keyWord, cursorScore, cursorId, size);

        String next = null;
        SearchDto last = res.size() > 0 ? res.get(res.size() - 1) : null;
        if (last != null) {
            next = toCursor(last.getScore(), last.getId());
        }

        return Response.okPagingAfter(res, next, size);
    }

    private String toCursor(Float score, String id) {

        return score + "_" + id;
    }

    private Object[] toCursors(String cursor) {
        if (cursor == null) {
            return new Object[]{};
        }
        String[] frags = cursor.split("_");
        if (frags.length != 2) {
            throw new InvalidOperationException("Wrong cursor.");
        }
        Float score = Float.valueOf(frags[0]);
        String id = frags[1];

        return new Object[]{score, id};
    }
}
