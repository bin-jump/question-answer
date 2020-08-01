package com.zhang.ddd.domain.aggregate.user.repository;

import com.zhang.ddd.domain.aggregate.user.entity.User;

public interface UserRepository {

    String nextId();

    void save(User user);

    User findByName(String name);

    User findById(String id);
}
