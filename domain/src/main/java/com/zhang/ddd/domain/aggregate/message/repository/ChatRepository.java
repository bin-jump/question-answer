package com.zhang.ddd.domain.aggregate.message.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.message.entity.Chat;
import com.zhang.ddd.domain.aggregate.message.entity.Message;
import com.zhang.ddd.domain.aggregate.message.entity.valueobject.ChatChatter;

public interface ChatRepository {

    String nextId();

    void save(Chat chat);

    void update(Chat chat);

    Chat findByChatter(ChatChatter chatter);

    Chat findById(String chatId);

    List<Chat> findChats(String chatterId, MessagePaging paging);

}
