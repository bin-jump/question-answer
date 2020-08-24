package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.domain.aggregate.message.entity.Message;
import com.zhang.ddd.domain.aggregate.message.repository.MessagePaging;
import com.zhang.ddd.domain.aggregate.message.repository.MessageRepository;
import com.zhang.ddd.domain.shared.SequenceRepository;
import com.zhang.ddd.infrastructure.persistence.assembler.MessageAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.MessageMapper;
import com.zhang.ddd.infrastructure.persistence.po.MessagePO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    SequenceRepository sequenceRepository;

    @Override
    public Long nextId() {
        long id = sequenceRepository.nextId();
        return id;
    }
    @Override
    public void save(Message message) {
        MessagePO messagePO = MessageAssembler.toPO(message);
        messageMapper.insert(messagePO);
    }

    @Override
    public List<Message> findChatMessages(Long chatId, MessagePaging paging) {

        Long cursor = paging.getCursor();
        List<MessagePO> messagePOs = messageMapper.findByChatId(chatId, cursor, paging.getSize());

        return MessageAssembler.toDOs(messagePOs);
    }

    @Override
    public List<Message> findChatNewMessages(Long chatId, Long lastId, int size) {

        List<MessagePO> messagePOs = messageMapper.findNewByChatId(chatId, lastId, size);
        return MessageAssembler.toDOs(messagePOs);
    }
}
