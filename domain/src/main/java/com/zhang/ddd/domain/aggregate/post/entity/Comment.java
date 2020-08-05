package com.zhang.ddd.domain.aggregate.post.entity;

import com.zhang.ddd.domain.aggregate.post.entity.valueobject.CommentResourceType;
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
public class Comment extends Entity<Comment> {

    public Comment(String id, String authorId, String resourceId,
                   String body, CommentResourceType resourceType) {

        if (!StringUtils.hasText(id)) {
            throw new InvalidValueException("Comment id can not be empty.");
        }
        if (!StringUtils.hasText(resourceId)) {
            throw new InvalidValueException("Comment resourceId can not be empty.");
        }
        if (!StringUtils.hasText(authorId)) {
            throw new InvalidValueException("Comment authorId can not be empty.");
        }
        if (!StringUtils.hasText(body)) {
            throw new InvalidValueException("Comment body can not be empty.");
        }
        if (resourceType == null) {
            throw new InvalidValueException("Comment resourceType can not be empty.");
        }

        this.id = id;
        this.authorId = authorId;
        this.resourceId = resourceId;
        this.body = body;
        this.resourceType = resourceType;
        this.created = new Date();
    }

    private String id;

    private String resourceId;

    private String authorId;

    private String body;

    private CommentResourceType resourceType;

    private Date created;

}
