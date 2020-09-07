package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.domain.aggregate.post.entity.Comment;
import com.zhang.ddd.domain.aggregate.post.entity.valueobject.CommentResourceType;
import com.zhang.ddd.domain.aggregate.post.repository.CommentRepository;
import com.zhang.ddd.domain.aggregate.post.repository.PostPaging;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import com.zhang.ddd.domain.shared.SequenceRepository;
import com.zhang.ddd.infrastructure.persistence.assembler.CommentAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.CommentMapper;
import com.zhang.ddd.infrastructure.persistence.po.CommentPO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    SequenceRepository sequenceRepository;

    @Override
    public Long nextId() {
        long id = sequenceRepository.nextId();
        return id;
    }

    @Override
    public void save(Comment comment) {
        CommentPO commentPO = CommentAssembler.toPO(comment);
        commentMapper.insert(commentPO);
    }

    @Override
    public List<Comment> findByResourceId(Long resourceId, CommentResourceType resourceType,
                                          PostPaging postPaging) {
        Long cursor = postPaging.getCursor();
        List<CommentPO> commentPOs =
                commentMapper.findByResourceId(resourceId, resourceType,
                        cursor, postPaging.getSize());

        return CommentAssembler.toDOs(commentPOs);
    }
}
