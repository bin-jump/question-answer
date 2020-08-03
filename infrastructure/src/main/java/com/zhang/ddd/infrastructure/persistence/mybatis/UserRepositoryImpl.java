package com.zhang.ddd.infrastructure.persistence.mybatis;

import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.domain.exception.ConcurrentUpdateException;
import com.zhang.ddd.infrastructure.persistence.assembler.UserAssembler;
import com.zhang.ddd.infrastructure.persistence.mybatis.mapper.UserMapper;
import com.zhang.ddd.infrastructure.persistence.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    UserMapper userMapper;

    // TODO: use db sequence
    @Override
    public String nextId() {
        final String random = UUID.randomUUID().toString()
                .toLowerCase().replace("-", "");
        return random;
    }

    @Override
    public void save(User user) {
        userMapper.insert(UserAssembler.toPO(user));
    }

    @Override
    public void update(User user) {
        int num = userMapper.update(UserAssembler.toPO(user));
        if (num == 0) {
            throw new ConcurrentUpdateException("User concurrent update.");
        }
    }

    @Override
    public User findByName(String name) {
        UserPO userPO = userMapper.findByName(name);
        return UserAssembler.toDO(userPO);
    }

    @Override
    public User findById(String id) {
        UserPO userPO = userMapper.findById(id);
        return UserAssembler.toDO(userPO);
    }
}
