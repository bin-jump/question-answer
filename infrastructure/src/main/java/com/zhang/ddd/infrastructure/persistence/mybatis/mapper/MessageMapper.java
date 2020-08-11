package com.zhang.ddd.infrastructure.persistence.mybatis.mapper;

import java.util.List;
import com.zhang.ddd.infrastructure.persistence.po.MessagePO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper {

    int insert(MessagePO messagePO);

    List<MessagePO> findByChatId(long chatId, Long cursor, int size);

    List<MessagePO> findNewByChatId(long chatId, Long cursor, int size);

}
