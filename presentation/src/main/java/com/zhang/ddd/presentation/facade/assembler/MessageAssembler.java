package com.zhang.ddd.presentation.facade.assembler;

import com.zhang.ddd.domain.aggregate.message.entity.Message;
import com.zhang.ddd.presentation.facade.dto.message.MessageDto;

public class MessageAssembler {

    public static MessageDto toDTO(Message message, Long userId) {
        if (message == null) {
            return null;
        }
        MessageDto messageDto = MessageDto.builder()
                .id(message.getId())
                .created(message.getCreated().getTime())
                .body(message.getBody())
                .fromId(message.getFromId())
                .fromMe(message.getFromId().equals(userId))
                .build();

        return messageDto;
    }
}
