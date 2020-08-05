package com.zhang.ddd.domain.aggregate.user.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.user.entity.User;

public interface UserRepository {

    String nextId();

    void save(User user);

    void update(User user);

    User findByName(String name);

    User findById(String id);

    List<User> findByIds(List<String> ids);
}
