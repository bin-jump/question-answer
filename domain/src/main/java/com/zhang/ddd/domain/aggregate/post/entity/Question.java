package com.zhang.ddd.domain.aggregate.post.entity;

import java.util.ArrayList;
import java.util.List;
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
public class Question extends Entity<Question> {

    public static final int TAG_NUM_LIMIT = 5;

    public Question(String id, String title, String body, String authorId) {
        if (!StringUtils.hasText(id)) {
            throw new InvalidValueException("Question id can not be empty.");
        }
        if (!StringUtils.hasText(title)) {
            throw new InvalidValueException("Question title can not be empty.");
        }
        if (!StringUtils.hasText(authorId)) {
            throw new InvalidValueException("Question author can not be empty.");
        }
        this.id = id;
        this.title = title;
        this.body = body;
        this.authorId = authorId;
        this.created = new Date();
    }

    private String title;

    private String body;

    private String authorId;

    private Date created;

    private List<Tag> tags = new ArrayList<>();

    private int answerCount;

    private int commentCount;

    private int followCount;

    private int upvoteCount;

    private int downvoteCount;

    public void addTag(Tag tag) {
        if (tags.size() == TAG_NUM_LIMIT) {
            throw new InvalidValueException("Too many question tag.");
        }
        for (Tag t : tags) {
            if (t.sameContentAs(tag)) {
                return;
            }
        }
        tags.add(tag);
    }


}
