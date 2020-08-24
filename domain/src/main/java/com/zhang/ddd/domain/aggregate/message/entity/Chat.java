package com.zhang.ddd.domain.aggregate.message.entity;

import com.zhang.ddd.domain.aggregate.message.entity.valueobject.ChatChatter;
import com.zhang.ddd.domain.exception.InvalidOperationException;
import com.zhang.ddd.domain.shared.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

// TODO: write correct setters
@Getter
@Setter
@NoArgsConstructor
public class Chat extends Entity<Chat> {

    public Chat(Long userAId, Long userBId, Long id) {
        this.id = id;
        this.chatter = new ChatChatter(userAId, userBId);
        this.created = new Date();
    }

    private ChatChatter chatter;

    private Long topMessageId;

    private String topBody;

    private Date topMessageCreated;

    private int youUnreadCount;

    private int meUnreadCount;

    private Date created;

    public Long getMeId() {
        return this.chatter.getChatterMeId();
    }

    public Long getYouId() {
        return this.chatter.getChatterYouId();
    }

    public int getUnreadCount(String userId) {
        if (!getMeId().equals(userId) && !getYouId().equals(userId)) {
            throw new InvalidOperationException("Wrong userId.");
        }
        if (getMeId().equals(userId)) {
            return meUnreadCount;
        }
        return youUnreadCount;
    }

    public Long getChatWithId(String userId) {
        if (!getMeId().equals(userId) && !getYouId().equals(userId)) {
            throw new InvalidOperationException("Wrong userId.");
        }
        if (getMeId().equals(userId)) {
            return getYouId();
        }
        return getMeId();
    }

    public void readMessage(Long readerId) {
        boolean me = readerId.equals(getMeId());
        if (me) {
            meUnreadCount = 0;
        } else {
            youUnreadCount = 0;
        }

    }

    public void updateTopMessage(Message message) {
        this.topBody = message.getBody();
        this.topMessageId = message.getId();
        this.topMessageCreated = message.getCreated();

        boolean toMe = !message.getFromId().equals(getMeId());
        if (toMe) {
            meUnreadCount += 1;
        } else {
            youUnreadCount += 1;
        }
    }

}
