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

    private long id;

    private long version;

    private long parentId;

    private String body;

    private long authorId;

    private Date created;

    private int upvoteCount;

    private int downvoteCount;

    private int commentCount;
}
