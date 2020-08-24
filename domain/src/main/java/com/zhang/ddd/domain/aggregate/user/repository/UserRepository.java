package com.zhang.ddd.domain.aggregate.user.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.user.entity.User;

public interface UserRepository {

    Long nextId();

    void save(User user);

    void update(User user);

    User findByName(String name);

    User findById(Long id);

    List<User> findByIds(List<Long> ids);
}
