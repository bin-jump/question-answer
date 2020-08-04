package com.zhang.ddd.domain.aggregate.post.entity;

import com.zhang.ddd.domain.exception.InvalidValueException;
import com.zhang.ddd.domain.shared.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Date;

// TODO: write correct setters
@Getter
@Setter
@NoArgsConstructor
public class Answer extends Entity<Answer> {

    public Answer(String id, String parentId, String body, String authorId) {
        if (!StringUtils.hasText(id)) {
            throw new InvalidValueException("Answer id can not be empty.");
        }
        if (!StringUtils.hasText(body)) {
            throw new InvalidValueException("Answer body can not be empty.");
        }
        if (!StringUtils.hasText(parentId)) {
            throw new InvalidValueException("Answer parentId can not be empty.");
        }
        if (!StringUtils.hasText(authorId)) {
            throw new InvalidValueException("Answer author can not be empty.");
        }

        this.id = id;
        this.parentId = parentId;
        this.body = body;
        this.authorId = authorId;
        this.created = new Date();
    }

    private String parentId;

    private String body;

    private String authorId;

    private Date created;

    private int upvoteCount;

    private int downvoteCount;

    private int commentCount;


}
