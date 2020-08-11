package com.zhang.ddd.domain.aggregate.message.service;

import java.util.List;
import com.zhang.ddd.domain.aggregate.message.entity.Chat;
import com.zhang.ddd.domain.aggregate.message.entity.Message;
import com.zhang.ddd.domain.aggregate.message.entity.valueobject.ChatChatter;
import com.zhang.ddd.domain.aggregate.message.repository.ChatRepository;
import com.zhang.ddd.domain.aggregate.message.repository.MessagePaging;
import com.zhang.ddd.domain.aggregate.message.repository.MessageRepository;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageDomainService {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    MessageRepository messageRepository;

    public Chat sendMessage(String fromId, String toId, String body) {

        ChatChatter chatChatter = new ChatChatter(fromId, toId);
        Chat chat = chatRepository.findByChatter(chatChatter);

        // create new chat
        if (chat == null) {
            String chatId = chatRepository.nextId();
            chat = new Chat(fromId, toId, chatId);
            chatRepository.save(chat);
        }

        String mid = messageRepository.nextId();
        Message message = new Message(mid, chat.getId(), fromId, body);
        chat.updateTopMessage(message);

        messageRepository.save(message);
        chatRepository.update(chat);

        return chat;
    }

    public List<Message> readMessages(String readerId, String chatId, MessagePaging paging) {

        Chat chat = chatRepository.findById(chatId);
        if (chat == null) {
            throw new ResourceNotFoundException("Chat not found.");
        }

        chat.readMessage(readerId);
        chatRepository.update(chat);
        List<Message> messages = messageRepository.findChatMessages(chatId, paging);
        return messages;
    }

    public List<Message> readNewMessage(String readerId, String chatId, String lastMessageId, int size) {
        Chat chat = chatRepository.findById(chatId);
        if (chat == null) {
            throw new ResourceNotFoundException("Chat not found.");
        }
        size = Math.max(10, size);
        size = Math.min(200, size);

        chat.readMessage(readerId);
        chatRepository.update(chat);
        List<Message> messages = messageRepository.findChatNewMessages(chatId, lastMessageId, size);

        return messages;
    }

}
