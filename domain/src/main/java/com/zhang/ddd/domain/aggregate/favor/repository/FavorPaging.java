package com.zhang.ddd.domain.aggregate.favor.repository;

import com.zhang.ddd.domain.shared.Paging;
import lombok.Data;

@Data
public class FavorPaging extends Paging<String> {

    public static enum OrderBy {
        CREATE
    }

    public FavorPaging(String cursor, int size) {
        super(cursor, size);
    }

    public FavorPaging.OrderBy order = FavorPaging.OrderBy.CREATE;

}
