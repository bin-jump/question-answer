package com.zhang.ddd.domain.aggregate.message.entity;


import com.zhang.ddd.domain.shared.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

// TODO: write correct setters
@Getter
@Setter
@NoArgsConstructor
public class Message extends Entity<Message> {

    public Message(String id, String chatId, String fromId, String body) {
        this.id = id;
        this.chatId = chatId;
        this.fromId = fromId;
        this.body = body;
        this.created = new Date();
    }

    private String chatId;

    private String body;

    private Date created;

    private String fromId;

}
