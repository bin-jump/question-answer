package com.zhang.ddd.application.service;

import com.zhang.ddd.domain.aggregate.message.entity.Chat;
import com.zhang.ddd.domain.aggregate.message.entity.Message;
import com.zhang.ddd.domain.aggregate.message.repository.MessagePaging;
import com.zhang.ddd.domain.aggregate.message.service.MessageDomainService;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.domain.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageApplicationService {

    @Autowired
    MessageDomainService messageDomainService;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public Chat sendMessage(String fromId, String toId, String body) {

        User from = userRepository.findById(fromId);
        User to = userRepository.findById(toId);
        if (from == null || to == null) {
            throw new ResourceNotFoundException("User not found.");
        }

        Chat chat = messageDomainService.sendMessage(fromId, toId, body);
        return chat;
    }

    @Transactional
    public List<Message> readMessages(String readerId, String chatId, MessagePaging paging) {

        List<Message> messages = messageDomainService.readMessages(readerId, chatId, paging);
        return messages;
    }

    @Transactional
    public List<Message> readNewMessage(String readerId, String chatId, String lastMessageId, int size) {

        List<Message> messages = messageDomainService.readNewMessage(readerId, chatId, lastMessageId, size);
        return messages;
    }
}
