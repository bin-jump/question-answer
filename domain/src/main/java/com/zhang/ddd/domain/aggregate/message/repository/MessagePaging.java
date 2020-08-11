package com.zhang.ddd.domain.aggregate.message.repository;


import com.zhang.ddd.domain.shared.Paging;
import lombok.Data;

@Data
public class MessagePaging extends Paging<String> {

    public MessagePaging(String cursor, int size) {
        super(cursor, size);
    }

    public MessagePaging(int offset, int size) {
        super(offset, size);
    }

}
