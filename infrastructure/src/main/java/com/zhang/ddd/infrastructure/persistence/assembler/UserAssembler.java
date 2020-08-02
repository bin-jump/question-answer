package com.zhang.ddd.infrastructure.persistence.assembler;

import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.infrastructure.persistence.po.UserPO;
import org.springframework.beans.BeanUtils;

public class UserAssembler {

    public static UserPO toPO(User user) {
        if (user == null) {
            return null;
        }
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(user, userPO);
        userPO.setVersion(user.getVersion());
        userPO.setUserId(user.getId());

        return userPO;
    }

    public static User toDomainUser(UserPO userPO) {
        if (userPO == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userPO, user);
        user.setId(userPO.getUserId());

        return user;
    }
}
