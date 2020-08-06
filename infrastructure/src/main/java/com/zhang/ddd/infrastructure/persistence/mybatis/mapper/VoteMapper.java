package com.zhang.ddd.infrastructure.persistence.mybatis.mapper;

import com.zhang.ddd.domain.aggregate.vote.entity.valueobject.VoteResourceType;
import com.zhang.ddd.infrastructure.persistence.po.VotePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VoteMapper {

    VotePO find(String voterId, String resourceId, VoteResourceType resourceType);

    List<VotePO> findByResourceIds(String voterId, List<String> resourceIds, VoteResourceType resourceType);

    int insert(VotePO votePO);

    int update(VotePO votePO);

    int delete(VotePO votePO);
}
