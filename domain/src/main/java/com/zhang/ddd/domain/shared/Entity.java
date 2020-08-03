package com.zhang.ddd.domain.shared;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

// for simplicity, just use string as a common id type
@Getter
@Setter
public class Entity<T> implements Serializable {

    protected String id;

    protected long version = 1;
}
