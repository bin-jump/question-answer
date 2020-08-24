package com.zhang.ddd.domain.aggregate.post.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.Comment;
import com.zhang.ddd.domain.aggregate.post.entity.valueobject.CommentResourceType;

public interface CommentRepository {

    Long nextId();

    void save(Comment comment);

    List<Comment> findByResourceId(Long resourceId, CommentResourceType resourceType,
                                   PostPaging postPaging);
}
