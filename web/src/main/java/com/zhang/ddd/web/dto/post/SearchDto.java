package com.zhang.ddd.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchDto {

    String id;

    private float score;

    private String searchType;

    private QuestionDto question;

    private AnswerDTO answer;

    private long created;

}
