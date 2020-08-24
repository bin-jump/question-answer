package com.zhang.ddd.domain.aggregate.message.entity.valueobject;

import com.zhang.ddd.domain.exception.InvalidOperationException;
import lombok.Getter;

@Getter
public class ChatChatter {

    public ChatChatter(Long userAId, Long userBId) {
        if (userAId.equals(userBId)) {
            throw new InvalidOperationException("Invalid chat id.");
        }

        if (userAId.compareTo(userBId) > 0) {
            this.chatterMeId = userAId;
            this.chatterYouId = userBId;
        } else {
            this.chatterMeId = userBId;
            this.chatterYouId = userAId;
        }
    }

    private Long chatterYouId;

    private Long chatterMeId;
}
