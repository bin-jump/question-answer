package com.zhang.ddd.infrastructure.persistence.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.Follow;
import com.zhang.ddd.infrastructure.persistence.po.FollowPO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.BeanUtils;

public class FollowAssembler {

    public static FollowPO toPO(Follow follow) {
        if (follow == null) {
            return null;
        }
        FollowPO followPO = new FollowPO();
        BeanUtils.copyProperties(follow, followPO);
        followPO.setFollowerId(NumberEncoder.decode(follow.getFollowerId()));
        followPO.setResourceId(NumberEncoder.decode(follow.getResourceId()));

        return followPO;
    }

    public static List<Follow> toDOs(List<FollowPO> followPOs) {
        return followPOs.stream()
                .map(FollowAssembler::toDO).collect(Collectors.toList());
    }

    public static Follow toDO(FollowPO followPO) {
        if (followPO == null) {
            return null;
        }
        Follow follow = new Follow();
        BeanUtils.copyProperties(followPO, follow);
        follow.setFollowerId(NumberEncoder.encode(followPO.getFollowerId()));
        follow.setResourceId(NumberEncoder.encode(followPO.getResourceId()));

        return follow;
    }

}
