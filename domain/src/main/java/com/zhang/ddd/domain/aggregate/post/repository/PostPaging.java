package com.zhang.ddd.domain.aggregate.post.repository;

import com.zhang.ddd.domain.shared.Paging;
import lombok.Data;

@Data
public class PostPaging extends Paging<String> {

    public static enum OrderBy {
        CREATE
    }

    public PostPaging(String cursor, int size) {
        super(cursor, size);
    }

    public PostPaging(int offset, int size) {
        super(offset, size);
    }

    public OrderBy order = OrderBy.CREATE;

}
