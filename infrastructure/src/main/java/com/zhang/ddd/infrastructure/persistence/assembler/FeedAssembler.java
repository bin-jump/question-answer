package com.zhang.ddd.infrastructure.persistence.assembler;

import java.util.List;
import com.zhang.ddd.domain.aggregate.favor.entity.Feed;
import com.zhang.ddd.infrastructure.persistence.po.FeedPO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

public class FeedAssembler {

    public static FeedPO toPO(Feed feed) {
        if (feed == null) {
            return null;
        }
        FeedPO feedPO = new FeedPO();
        BeanUtils.copyProperties(feed, feedPO);
        feedPO.setVersion(feed.getVersion());
        feedPO.setId(NumberEncoder.decode(feed.getId()));
        feedPO.setResourceId(NumberEncoder.decode(feed.getResourceId()));
        feedPO.setCreatorId(NumberEncoder.decode(feed.getCreatorId()));

        return feedPO;
    }

    public static List<Feed> toDOs(List<FeedPO> feedPOs) {

        return feedPOs.stream()
                .map(FeedAssembler::toDO).collect(Collectors.toList());
    }

    public static Feed toDO(FeedPO feedPO) {
        if (feedPO == null) {
            return null;
        }
        Feed feed = new Feed();
        BeanUtils.copyProperties(feedPO, feed);
        feed.setId(NumberEncoder.encode(feedPO.getId()));
        feed.setResourceId(NumberEncoder.encode(feedPO.getResourceId()));
        feed.setCreatorId(NumberEncoder.encode(feedPO.getCreatorId()));

        return feed;
    }
}
