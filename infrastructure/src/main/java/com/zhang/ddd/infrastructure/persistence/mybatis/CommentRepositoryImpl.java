package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.domain.aggregate.post.entity.Comment;
import com.zhang.ddd.domain.aggregate.post.entity.valueobject.CommentResourceType;
import com.zhang.ddd.domain.aggregate.post.repository.CommentRepository;
import com.zhang.ddd.domain.aggregate.post.repository.PostPaging;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import com.zhang.ddd.infrastructure.persistence.assembler.CommentAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.CommentMapper;
import com.zhang.ddd.infrastructure.persistence.po.CommentPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public String nextId() {
        final String random = UUID.randomUUID().toString()
                .toLowerCase().replace("-", "");
        return random;
    }

    @Override
    public void save(Comment comment) {
        CommentPO commentPO = CommentAssembler.toPO(comment);
        commentMapper.insert(commentPO);
    }

    @Override
    public List<Comment> findByResourceId(String resourceId, CommentResourceType resourceType,
                                          PostPaging postPaging) {
        String sortKey = "id";
        Long cursor = null;
        if (postPaging.getCursor() != null) {
            CommentPO cursorComment =
                    commentMapper.findById(postPaging.getCursor());
            if (cursorComment == null) {
                throw new ResourceNotFoundException("Comment not found.");
            }
            cursor = cursorComment.getId();
        }

        List<CommentPO> commentPOs =
                commentMapper.findByResourceId(resourceId, resourceType,
                        cursor, postPaging.getSize(), sortKey);

        return CommentAssembler.toDOs(commentPOs);
    }
}
