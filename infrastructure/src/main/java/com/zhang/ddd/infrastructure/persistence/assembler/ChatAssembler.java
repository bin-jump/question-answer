package com.zhang.ddd.infrastructure.persistence.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.message.entity.Chat;
import com.zhang.ddd.domain.aggregate.message.entity.valueobject.ChatChatter;
import com.zhang.ddd.infrastructure.persistence.po.ChatPO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.BeanUtils;

public class ChatAssembler {

    public static ChatPO toPO(Chat chat) {
        if (chat == null) {
            return null;
        }
        ChatPO chatPO = new ChatPO();
        BeanUtils.copyProperties(chat, chatPO);
        chatPO.setVersion(chat.getVersion());
        chatPO.setId(NumberEncoder.decode(chat.getId()));
        chatPO.setChatterMeId(NumberEncoder.decode(chat.getChatter().getChatterMeId()));
        chatPO.setChatterYouId(NumberEncoder.decode(chat.getChatter().getChatterYouId()));

        if (chat.getTopMessageId() != null) {
            chatPO.setTopMessageId(NumberEncoder.decode(chat.getTopMessageId()));
        }
        return chatPO;
    }

    public static List<Chat> toDOs(List<ChatPO> chatPOs) {

        return chatPOs.stream()
                .map(ChatAssembler::toDO).collect(Collectors.toList());
    }

    public static Chat toDO(ChatPO chatPO) {
        if (chatPO == null) {
            return null;
        }
        Chat chat = new Chat();
        BeanUtils.copyProperties(chatPO, chat);
        chat.setVersion(chatPO.getVersion());
        ChatChatter chatChatter = new ChatChatter(
                NumberEncoder.encode(chatPO.getChatterMeId()),
                NumberEncoder.encode(chatPO.getChatterYouId()));

        chat.setChatter(chatChatter);
        chat.setId(NumberEncoder.encode(chatPO.getId()));
        if (chatPO.getTopMessageId() != null) {
            chat.setTopMessageId(NumberEncoder.encode(chatPO.getTopMessageId()));
        }

        return chat;
    }
}
