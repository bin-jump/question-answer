package com.zhang.ddd.infrastructure.persistence;

import com.zhang.ddd.domain.aggregate.user.repository.AvatarImageRepository;
import com.zhang.ddd.infrastructure.util.MD5Util;
import org.springframework.stereotype.Component;

@Component
public class AvatarImageRepositoryImpl implements AvatarImageRepository {

    @Override
    public String nameHashImage(String name) {
        String hash = MD5Util.md5Hex(name);
        return String.format("https://www.gravatar.com/avatar/%s.jpg?s=400&d=identicon", hash);
    }
}
