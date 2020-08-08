package com.zhang.ddd.infrastructure.persistence.po;

import com.zhang.ddd.domain.aggregate.post.entity.valueobject.CommentResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPO {

    private long id;

    private long version;

    private long resourceId;

    private long authorId;

    private String body;

    private CommentResourceType resourceType;

    private Date created;
}
