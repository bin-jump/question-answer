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

    public void setSize(int size) {
        size = Math.max(1, size);
        size = Math.min(20, size);
        this.size = size;
    }

    public boolean hasCursor() {
        return cursorId != null && cursorScore != null;
    }

}
