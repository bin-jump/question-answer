package com.zhang.ddd.infrastructure.persistence.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class QuestionTag {

    public QuestionTag(String questionId, String tagId) {
        this.questionId = questionId;
        this.tagId = tagId;
    }

    private String questionId;

    private String tagId;

    // for navigation
    private TagPO tagPO;
}
