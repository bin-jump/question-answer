package com.zhang.ddd.infrastructure.persistence.assembler;

import com.zhang.ddd.domain.aggregate.message.entity.Message;
import com.zhang.ddd.infrastructure.persistence.po.MessagePO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MessageAssembler {

    public static MessagePO toPO(Message message) {
        if (message == null) {
            return null;
        }
        MessagePO messagePO = new MessagePO();
        BeanUtils.copyProperties(message, messagePO);
        messagePO.setId(NumberEncoder.decode(message.getId()));
        messagePO.setChatId(NumberEncoder.decode(message.getChatId()));
        messagePO.setFromId(NumberEncoder.decode(message.getFromId()));

        return messagePO;
    }

    public static List<Message> toDOs(List<MessagePO> messagePOs) {

        return messagePOs.stream()
                .map(MessageAssembler::toDO).collect(Collectors.toList());
    }

    public static Message toDO(MessagePO messagePO) {
        if (messagePO == null) {
            return null;
        }
        Message message = new Message();
        BeanUtils.copyProperties(messagePO, message);
        message.setId(NumberEncoder.encode(messagePO.getId()));
        message.setChatId(NumberEncoder.encode(messagePO.getChatId()));
        message.setFromId(NumberEncoder.encode(messagePO.getFromId()));

        return message;
    }
}
