package com.zhang.ddd.infrastructure.persistence.mybatis.mapper;

import com.zhang.ddd.domain.aggregate.post.entity.valueobject.CommentResourceType;
import com.zhang.ddd.infrastructure.persistence.po.CommentPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    int insert(CommentPO commentPO);

    CommentPO findById(long id);

    List<CommentPO> findByResourceId(long resourceId, CommentResourceType resourceType,
                                     Long cursor, int size, String sortKey);

}
