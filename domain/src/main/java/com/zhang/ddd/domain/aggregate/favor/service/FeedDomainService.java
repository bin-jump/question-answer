package com.zhang.ddd.domain.aggregate.favor.service;

import com.zhang.ddd.domain.aggregate.favor.entity.Feed;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedAction;
import com.zhang.ddd.domain.aggregate.favor.entity.valueobject.FeedType;
import com.zhang.ddd.domain.aggregate.favor.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedDomainService {

    @Autowired
    FeedRepository feedRepository;

    public void questionCreate(Long creatorId, Long questionId) {
        Feed feed = createFeed(questionId, creatorId, FeedType.QUESTION, FeedAction.CREATE);
    }

    public void answerCreate(Long creatorId, Long answerId) {
        Feed feed = createFeed(answerId, creatorId, FeedType.ANSWER, FeedAction.CREATE);

    }

    public void questionFollow(Long creatorId, Long questionId) {
        Feed feed = createFeed(questionId, creatorId, FeedType.QUESTION, FeedAction.FOLLOW);

    }

    public void userFollow(Long creatorId, Long userId) {
        Feed feed = createFeed(userId, creatorId, FeedType.USER, FeedAction.FOLLOW);

    }

    public void questionUnfollow(Long creatorId, Long questionId) {
        removeFeed(questionId, creatorId ,FeedType.QUESTION, FeedAction.FOLLOW);

    }

    public void userUnfollow(Long creatorId, Long userId) {
        removeFeed(userId, creatorId ,FeedType.USER, FeedAction.FOLLOW);
    }

    private Feed createFeed(Long resourceId, Long creatorId, FeedType feedType, FeedAction feedAction) {

        Long id = feedRepository.nextId();
        Feed feed = new Feed(id, feedType, feedAction, resourceId, creatorId);

        feedRepository.save(feed);
        return feed;
    }

    private Feed removeFeed(Long resourceId, Long creatorId, FeedType feedType, FeedAction feedAction) {

        Feed feed = feedRepository.find(creatorId, resourceId, feedType, feedAction);
        if (feed != null) {
            feedRepository.remove(feed);
        }

        return feed;
    }

}
