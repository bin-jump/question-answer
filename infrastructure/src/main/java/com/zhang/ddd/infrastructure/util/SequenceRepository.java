package com.zhang.ddd.infrastructure.util;

import org.springframework.stereotype.Repository;


public interface SequenceRepository {

    long nextId();
}
