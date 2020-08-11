package com.zhang.ddd.application.service;

import java.util.List;
import com.zhang.ddd.domain.aggregate.message.entity.Chat;
import com.zhang.ddd.domain.aggregate.message.repository.ChatRepository;
import com.zhang.ddd.domain.aggregate.message.repository.MessagePaging;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.presentation.SmartpostApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ComponentScan("com.zhang.ddd")
@SpringBootTest(classes = SmartpostApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Slf4j
public class ChatServiceTest {

    String fromName = "zhang";

    String toName = "user1";

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageApplicationService messageApplicationService;

    @Autowired
    ChatRepository chatRepository;

    @Test
    public void sendMessageTest() {
        User from = userRepository.findByName(fromName);
        User to = userRepository.findByName(toName);

        Chat chat = messageApplicationService.sendMessage(from.getId(), to.getId(), "This is a message.");
        List<Chat> chats = chatRepository
                .findChats(to.getId(), new MessagePaging(null, 10));
        int unread = chats.get(0).getUnreadCount(to.getId());
        Assert.assertNotNull(chat);
        Assert.assertTrue(unread > 0);
    }
}
