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
    public Follow find(String followerId, String resourceId, FollowResourceType resourceType) {
        long fid = NumberEncoder.decode(followerId);
        long rid = NumberEncoder.decode(resourceId);

        FollowPO followPO = followMapper.find(fid, rid, resourceType);
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
        long fid = NumberEncoder.decode(followerId);
        List<Long> rids = resourceIds.stream().map(NumberEncoder::decode).collect(Collectors.toList());

        List<FollowPO> followPOs = followMapper.findByResourceIds(fid, rids, resourceType);
        return FollowAssembler.toDOs(followPOs);
    }

    @Override
    public List<Follow> findFollowed(String followId, FollowResourceType resourceType, FavorPaging paging) {
        long fid = NumberEncoder.decode(followId);

        List<FollowPO> followPOs = followMapper.findFollowed(fid, resourceType,
                paging.getCursor(), paging.getSize());

        return FollowAssembler.toDOs(followPOs);
    }

    @Override
    public List<Follow> findFollowee(String resourceId, FollowResourceType resourceType, FavorPaging paging) {
        long rid = NumberEncoder.decode(resourceId);
        List<FollowPO> followPOs = followMapper.findFollowed(rid, resourceType,
                paging.getCursor(), paging.getSize());

        return FollowAssembler.toDOs(followPOs);
    }
}
