package com.zhang.ddd.presentation.facade;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.zhang.ddd.application.service.MessageApplicationService;
import com.zhang.ddd.domain.aggregate.message.entity.Chat;
import com.zhang.ddd.domain.aggregate.message.entity.Message;
import com.zhang.ddd.domain.aggregate.message.repository.ChatRepository;
import com.zhang.ddd.domain.aggregate.message.repository.MessagePaging;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.presentation.facade.assembler.ChatAssembler;
import com.zhang.ddd.presentation.facade.assembler.MessageAssembler;
import com.zhang.ddd.presentation.facade.assembler.UserAssembler;
import com.zhang.ddd.presentation.facade.dto.message.ChatDto;
import com.zhang.ddd.presentation.facade.dto.message.MessageDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceFacade {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageApplicationService messageApplicationService;

    @Autowired
    ChatRepository chatRepository;

    public List<ChatDto> getUserChats(UserDto currentUser, String cursor, int size) {

        List<Chat> chats = chatRepository.findChats(currentUser.getId(), new MessagePaging(cursor, size));
        List<ChatDto> chatDtos = ChatAssembler.toDTOs(chats, currentUser.getId());
        fillChatUser(chatDtos);
        return chatDtos;
    }

    public ChatDto sendMessage(String fromId, String toId, String body) {
        Chat chat = messageApplicationService.sendMessage(fromId, toId, body);
        ChatDto chatDto = ChatAssembler.toDTO(chat, fromId);
        MessageDto messageDto = MessageDto.builder()
                .fromMe(true)
                .fromId(fromId)
                .body(chat.getTopBody())
                .id(chat.getTopMessageId())
                .created(chat.getTopMessageCreated().getTime())
                .build();
        chatDto.getMessages().add(messageDto);
        fillChatUser(Arrays.asList(chatDto));

        return chatDto;
    }

    public List<MessageDto> getUserMessage(UserDto currentUser, String chatId, String cursor, int size) {
        List<Message> messages = messageApplicationService.readMessages(currentUser.getId(), chatId,
                new MessagePaging(cursor, size));

        List<MessageDto> messageDtos = messages.stream()
                .map(e -> MessageAssembler.toDTO(e, currentUser.getId()))
                .collect(Collectors.toList());

        return messageDtos;
    }

    public List<MessageDto> getUserNewMessage(UserDto currentUser, String chatId, String lastMessageId, int size) {
        List<MessageDto> messages = messageApplicationService
                .readNewMessage(currentUser.getId(), chatId, lastMessageId, size)
                .stream().map(e -> MessageAssembler.toDTO(e, currentUser.getId()))
                .collect(Collectors.toList());

        return messages;
    }

    private void fillChatUser(List<ChatDto> chatDtos) {
        Map<String, UserDto> userMapping = userRepository.findByIds(chatDtos.stream()
                .map(ChatDto::getWithId).collect(Collectors.toList()))
                .stream().map(UserAssembler::toDTO).collect(Collectors.toMap(UserDto::getId, e -> e));

        chatDtos.stream().forEach(e -> {
            UserDto user = userMapping.get(e.getWithId());
            e.setWithUser(user);
        });

    }
}
