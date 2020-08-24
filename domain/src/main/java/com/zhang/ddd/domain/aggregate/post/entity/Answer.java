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

    public Answer(Long id, Long parentId, String body, Long authorId) {

        if (!StringUtils.hasText(body)) {
            throw new InvalidValueException("Answer body can not be empty.");
        }

        this.id = id;
        this.parentId = parentId;
        this.body = body;
        this.authorId = authorId;
        this.created = new Date();
    }

    private Long parentId;

    private String body;

    private Long authorId;

    private Date created;

    private int upvoteCount;

    private int downvoteCount;

    private int commentCount;


}
