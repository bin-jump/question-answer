package com.zhang.ddd.infrastructure.persistence.po;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionPO {

    private Long id;

    private String questionId;

    private long version;

    private String title;

    private String body;

    private String authorId;

    private Date created;

    private List<TagPO> tags = new ArrayList<>();

    private int answerCount;

    private int commentCount;

    private int followCount;

    private int upvoteCount;

    private int downvoteCount;
}
