package com.zhang.ddd.domain.aggregate.post.entity;

import com.zhang.ddd.domain.aggregate.post.entity.valueobject.SearchItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

// TODO: write correct setters
@Getter
@Setter
@NoArgsConstructor
public class SearchItem {

    public static SearchItem fromQuestion(Question question) {
        SearchItem item = new SearchItem();
        item.id = question.getId();
        item.itemType = SearchItemType.QUESTION;
        item.authorId = question.getAuthorId();
        item.title = question.getTitle();
        item.body = question.getBody();
        item.created = new Date();

        return item;
    }

    public static SearchItem fromAnswer(Answer answer, String questionTitle) {

        SearchItem item = new SearchItem();
        item.id = answer.getId();
        item.itemType = SearchItemType.ANSWER;
        item.authorId = answer.getAuthorId();
        item.parentId = answer.getParentId();
        item.title = questionTitle;
        item.body = answer.getBody();
        item.created = new Date();

        return item;
    }

    private Long id;

    private SearchItemType itemType;

    private Long authorId;

    private Long parentId;

    private String title;

    private String body;

    private Date created;

    private Float score;

}
