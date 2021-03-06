package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.domain.aggregate.favor.entity.Feed;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedAction;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedType;
import com.zhang.ddd.domain.aggregate.favor.repository.FavorPaging;
import com.zhang.ddd.domain.aggregate.favor.repository.FeedRepository;
import com.zhang.ddd.domain.shared.SequenceRepository;
import com.zhang.ddd.infrastructure.persistence.assembler.FeedAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.FeedMapper;
import com.zhang.ddd.infrastructure.persistence.po.FeedPO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FeedRepositoryImpl implements FeedRepository {

    @Autowired
    FeedMapper feedMapper;

    @Autowired
    SequenceRepository sequenceRepository;

    @Override
    public Long nextId() {
        long id = sequenceRepository.nextId();
        return id;
    }

    @Override
    public void save(Feed feed) {
        FeedPO feedPO = FeedAssembler.toPO(feed);
        feedMapper.insert(feedPO);
    }

    @Override
    public void remove(Feed feed) {
        FeedPO feedPO = FeedAssembler.toPO(feed);
        feedMapper.delete(feedPO);
    }

    @Override
    public Feed find(Long creatorId, Long resourceId, FeedType feedType, FeedAction feedAction) {

        FeedPO feedPO = feedMapper.find(creatorId, resourceId, feedType, feedAction);
        return FeedAssembler.toDO(feedPO);
    }

    @Override
    public List<Feed> getUserFeed(Long userId, FavorPaging paging) {

        Long cursor =paging.getCursor();
        List<FeedPO> feedPOs = feedMapper.findUseFeed(userId, cursor, paging.getSize());

        return feedPOs.stream().map(FeedAssembler::toDO).collect(Collectors.toList());
    }
}
