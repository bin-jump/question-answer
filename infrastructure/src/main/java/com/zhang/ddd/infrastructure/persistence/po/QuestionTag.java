package com.zhang.ddd.infrastructure.persistence.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class QuestionTag {

    public QuestionTag(long questionId, long tagId) {
        this.questionId = questionId;
        this.tagId = tagId;
    }

    private long questionId;

    private long tagId;

    // for navigation
    private TagPO tagPO;
}
