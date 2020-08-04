package com.zhang.ddd.infrastructure.persistence.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerPO {

    private Long id;

    private String answerId;

    private long version;

    private String parentId;

    private String body;

    private String authorId;

    private Date created;

    private int upvoteCount;

    private int downvoteCount;

    private int commentCount;
}
