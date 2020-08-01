package com.zhang.ddd.domain.shared;

import lombok.Getter;

// for simplicity, just use string as a common id type
@Getter
public class Entity<T> {

    protected String id;

    protected long version = 1;
}
