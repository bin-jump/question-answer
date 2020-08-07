package com.zhang.ddd.domain.shared;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

@Getter
public class Paging<T> {

    public Paging(T cursor, int size) {
        this.cursor = cursor;
        setSize(size);
        byCursor = true;
    }

    public Paging(int offset, int size) {
        this.offset = offset;
        setSize(size);
        byOffset = true;
    }

    private int size;

    private T cursor;

    private int offset;

    boolean byCursor;

    boolean byOffset;

    public void setSize(int size) {
        size = Math.max(size, 1);
        size = Math.min(size, 20);
        this.size = size;
    }
}
