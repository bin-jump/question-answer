package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.domain.aggregate.message.entity.Chat;
import com.zhang.ddd.domain.aggregate.message.entity.valueobject.ChatChatter;
import com.zhang.ddd.domain.aggregate.message.repository.ChatRepository;
import com.zhang.ddd.domain.aggregate.message.repository.MessagePaging;
import com.zhang.ddd.domain.shared.SequenceRepository;
import com.zhang.ddd.infrastructure.persistence.assembler.ChatAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.ChatMapper;
import com.zhang.ddd.infrastructure.persistence.po.ChatPO;
import com.zhang.ddd.infrastructure.util.NumberEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRepositoryImpl implements ChatRepository {

    @Autowired
    ChatMapper chatMapper;

    @Autowired
    SequenceRepository sequenceRepository;

    @Override
    public Long nextId() {
        long id = sequenceRepository.nextId();
        return id;
    }

    @Override
    public void save(Chat chat) {
        ChatPO chatPO = ChatAssembler.toPO(chat);
        chatMapper.insert(chatPO);
    }

    @Override
    public void update(Chat chat) {
        ChatPO chatPO = ChatAssembler.toPO(chat);
        chatMapper.update(chatPO);
    }

    @Override
    public Chat findByChatter(ChatChatter chatter) {
        long meId =chatter.getChatterMeId();
        long youId =chatter.getChatterYouId();

        ChatPO chatPO = chatMapper.findChat(meId, youId);
        return ChatAssembler.toDO(chatPO);
    }

    @Override
    public Chat findById(Long chatId) {

        ChatPO chatPO = chatMapper.findById(chatId);
        return ChatAssembler.toDO(chatPO);
    }

    @Override
    public List<Chat> findChats(Long chatterId, MessagePaging paging) {

        Long cursor = paging.getCursor();
        List<ChatPO> chatPOs = chatMapper.findChats(chatterId, cursor, paging.getSize());
        return ChatAssembler.toDOs(chatPOs);
    }
}
