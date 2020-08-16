package com.zhang.ddd.domain.aggregate.post.repository;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostSearchQuery {

    private String keyWord;

    private String cursorId;

    private Float cursorScore;

    private int size;

    public boolean hasCursor() {
        return cursorId != null && cursorScore != null;
    }

}
