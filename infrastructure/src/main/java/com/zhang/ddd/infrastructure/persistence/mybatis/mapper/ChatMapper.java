package com.zhang.ddd.infrastructure.persistence.mybatis.mapper;

import java.util.List;
import com.zhang.ddd.infrastructure.persistence.po.ChatPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper {

    int insert(ChatPO chatPO);

    int update(ChatPO chatPO);

    ChatPO findById(long id);

    ChatPO findChat(long meId, long youId);

    List<ChatPO> findChats(long userId, Long cursor, int size);
}
