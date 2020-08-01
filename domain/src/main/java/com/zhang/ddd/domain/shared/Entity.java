package com.zhang.ddd.domain.shared;

import lombok.Getter;
import lombok.Setter;

// for simplicity, just use string as a common id type
@Getter
@Setter
public class Entity<T> {

    protected String id;

    protected long version = 1;
}
