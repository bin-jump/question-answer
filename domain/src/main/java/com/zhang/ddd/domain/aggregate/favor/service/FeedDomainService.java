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

    public void questionCreate(String creatorId, String questionId) {
        Feed feed = createFeed(questionId, creatorId, FeedType.QUESTION, FeedAction.CREATE);
    }

    public void answerCreate(String creatorId, String answerId) {
        Feed feed = createFeed(answerId, creatorId, FeedType.ANSWER, FeedAction.CREATE);

    }

    public void questionFollow(String creatorId, String questionId) {
        Feed feed = createFeed(questionId, creatorId, FeedType.QUESTION, FeedAction.FOLLOW);

    }

    public void userFollow(String creatorId, String userId) {
        Feed feed = createFeed(userId, creatorId, FeedType.USER, FeedAction.FOLLOW);

    }

    public void questionUnfollow(String creatorId, String questionId) {
        removeFeed(questionId, creatorId ,FeedType.QUESTION, FeedAction.FOLLOW);

    }

    public void userUnfollow(String creatorId, String userId) {
        removeFeed(userId, creatorId ,FeedType.USER, FeedAction.FOLLOW);
    }

    private Feed createFeed(String resourceId, String creatorId, FeedType feedType, FeedAction feedAction) {

        String id = feedRepository.nextId();
        Feed feed = new Feed(id, feedType, feedAction, resourceId, creatorId);

        feedRepository.save(feed);
        return feed;
    }

    private Feed removeFeed(String resourceId, String creatorId, FeedType feedType, FeedAction feedAction) {

        Feed feed = feedRepository.find(creatorId, resourceId, feedType, feedAction);
        if (feed != null) {
            feedRepository.remove(feed);
        }

        return feed;
    }

}
