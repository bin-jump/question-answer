package com.zhang.ddd.domain.aggregate.post.repository;

import com.zhang.ddd.domain.shared.Paging;
import lombok.Data;

@Data
public class PostPaging extends Paging<Long> {

    public static enum OrderBy {
        CREATE
    }

    public PostPaging(Long cursor, int size) {
        super(cursor, size);
    }

    public PostPaging(int offset, int size) {
        super(offset, size);
    }

    public OrderBy order = OrderBy.CREATE;

}
