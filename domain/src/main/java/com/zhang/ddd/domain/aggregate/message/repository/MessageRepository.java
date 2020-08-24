package com.zhang.ddd.domain.aggregate.message.repository;

import com.zhang.ddd.domain.aggregate.message.entity.Message;

import java.util.List;

public interface MessageRepository {

    Long nextId();

    void save(Message message);

    List<Message> findChatMessages(Long chatId, MessagePaging paging);

    List<Message> findChatNewMessages(Long chatId, Long lastId, int size);


}
