package com.zhang.ddd.domain.aggregate.favor.repository;

import com.zhang.ddd.domain.shared.Paging;
import lombok.Data;

@Data
public class FavorPaging extends Paging<Long> {

    public static enum OrderBy {
        CREATE
    }

    public FavorPaging(Long cursor, int size) {
        super(cursor, size);
    }

    public FavorPaging.OrderBy order = FavorPaging.OrderBy.CREATE;

}
