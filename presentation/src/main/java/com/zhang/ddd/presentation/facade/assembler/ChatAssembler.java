package com.zhang.ddd.presentation.facade.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.message.entity.Chat;
import com.zhang.ddd.presentation.facade.dto.message.ChatDto;

public class ChatAssembler {

    public static ChatDto toDTO(Chat chat, Long userId) {
        if (chat == null) {
            return null;
        }
        ChatDto chatDto = ChatDto.builder()
                .id(chat.getId())
                .coverText(chat.getTopBody())
                .unreadCount(chat.getUnreadCount(userId))
                .lastTime(chat.getTopMessageCreated().getTime())
                .withId(chat.getChatWithId(userId))
                .coverId(chat.getTopMessageId())
                .messages(new ArrayList<>())
                .build();

        return chatDto;
    }

    public static List<ChatDto> toDTOs(List<Chat> chats, Long userId) {

        return chats.stream().map(e -> ChatAssembler.toDTO(e, userId))
                .collect(Collectors.toList());
    }

}
