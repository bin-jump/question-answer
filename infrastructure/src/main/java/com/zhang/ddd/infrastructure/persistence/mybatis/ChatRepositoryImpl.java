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
    public String nextId() {
        long id = sequenceRepository.nextId();
        return NumberEncoder.encode(id);
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
        long meId = NumberEncoder.decode(chatter.getChatterMeId());
        long youId = NumberEncoder.decode(chatter.getChatterYouId());

        ChatPO chatPO = chatMapper.findChat(meId, youId);
        return ChatAssembler.toDO(chatPO);
    }

    @Override
    public Chat findById(String chatId) {
        ChatPO chatPO = chatMapper.findById(NumberEncoder.decode(chatId));
        return ChatAssembler.toDO(chatPO);
    }

    @Override
    public List<Chat> findChats(String chatterId, MessagePaging paging) {

        long uid = NumberEncoder.decode(chatterId);
        Long cursor = NumberEncoder.decode(paging.getCursor());
        List<ChatPO> chatPOs = chatMapper.findChats(uid, cursor, paging.getSize());

        return ChatAssembler.toDOs(chatPOs);
    }
}
