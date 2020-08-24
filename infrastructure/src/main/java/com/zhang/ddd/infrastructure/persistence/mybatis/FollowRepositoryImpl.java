package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.Follow;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FollowResourceType;
import com.zhang.ddd.domain.aggregate.favor.repository.FavorPaging;
import com.zhang.ddd.domain.aggregate.favor.repository.FollowRepository;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import com.zhang.ddd.infrastructure.persistence.assembler.FollowAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.FollowMapper;
import com.zhang.ddd.infrastructure.persistence.po.FollowPO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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
    public Follow find(Long followerId, Long resourceId, FollowResourceType resourceType) {

        FollowPO followPO = followMapper.find(followerId, resourceId, resourceType);
        return FollowAssembler.toDO(followPO);
    }

    @Override
    public void remove(Follow follow) {
        FollowPO followPO = FollowAssembler.toPO(follow);
        followMapper.delete(followPO);
    }

    @Override
    public List<Follow> findByResourceIds(Long followerId, List<Long> resourceIds,
                                          FollowResourceType resourceType) {

        List<FollowPO> followPOs = followMapper.findByResourceIds(followerId, resourceIds, resourceType);
        return FollowAssembler.toDOs(followPOs);
    }

    @Override
    public List<Follow> findFollowed(Long followId, FollowResourceType resourceType, FavorPaging paging) {

        Long cursor = paging.getCursor();
        List<FollowPO> followPOs = followMapper.findFollowed(followId, resourceType,
                cursor, paging.getSize());

        return FollowAssembler.toDOs(followPOs);
    }

    @Override
    public List<Follow> findFollower(Long resourceId, FollowResourceType resourceType, FavorPaging paging) {
        Long cursor = paging.getCursor();

        List<FollowPO> followPOs = followMapper.findResourceFollower(resourceId, resourceType,
                cursor, paging.getSize());

        return FollowAssembler.toDOs(followPOs);
    }
}
