package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.Follow;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FollowResourceType;
import com.zhang.ddd.domain.aggregate.favor.repository.FavorPaging;
import com.zhang.ddd.domain.aggregate.favor.repository.FollowRepository;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import com.zhang.ddd.infrastructure.persistence.assembler.FollowAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.FollowMapper;
import com.zhang.ddd.infrastructure.persistence.po.FollowPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FollowRepositoryImpl implements FollowRepository {

    @Autowired
    FollowMapper followMapper;

    @Override
    public void save(Follow follow) {
        FollowPO followPO = FollowAssembler.toPO(follow);
        followMapper.insert(followPO);
    }

    @Override
    public Follow find(String followerId, String resourceId, FollowResourceType resourceType) {

        FollowPO followPO = followMapper.find(followerId, resourceId, resourceType);
        return FollowAssembler.toDO(followPO);
    }

    @Override
    public void remove(Follow follow) {
        FollowPO followPO = FollowAssembler.toPO(follow);
        followMapper.delete(followPO);
    }

    @Override
    public List<Follow> findByResourceIds(String followerId, List<String> resourceIds,
                                          FollowResourceType resourceType) {
        List<FollowPO> followPOs = followMapper.findByResourceIds(followerId, resourceIds, resourceType);
        return FollowAssembler.toDOs(followPOs);
    }

    @Override
    public List<Follow> findFollowed(String followId, FollowResourceType resourceType, FavorPaging paging) {
        String sortKey = "id";
        Long cursor = null;
        if (paging.getCursor() != null) {
            FollowPO cursorQuestion = followMapper.find(followId, paging.getCursor(), resourceType);
            if (cursorQuestion == null) {
                throw new ResourceNotFoundException("Follow info not found");
            }
            cursor = cursorQuestion.getId();
        }

        return null;
    }

    @Override
    public List<Follow> findFollowee(String resourceId, FollowResourceType resourceType, FavorPaging paging) {
        return null;
    }
}
