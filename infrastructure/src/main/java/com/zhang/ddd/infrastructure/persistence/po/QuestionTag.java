package com.zhang.ddd.infrastructure.persistence.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionTag {

    private String questionId;

    private String tagId;
}
