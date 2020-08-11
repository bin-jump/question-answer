package com.zhang.ddd.domain.aggregate.message.repository;

import com.zhang.ddd.domain.aggregate.message.entity.Message;

import java.util.List;

public interface MessageRepository {

    String nextId();

    void save(Message message);

    List<Message> findChatMessages(String chatId, MessagePaging paging);

    List<Message> findChatNewMessages(String chatId, String lastId, int size);


}
