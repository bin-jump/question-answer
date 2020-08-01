package com.zhang.ddd.presentation.facade;

import com.zhang.ddd.application.service.UserApplicationService;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.presentation.facade.assembler.UserAssembler;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceFacade {

    @Autowired
    UserApplicationService userApplicationService;

    public UserDto create(UserDto userDto) {

        User user = userApplicationService.create(userDto.getName(), userDto.getPassword());

        return UserAssembler.toDTO(user);
    }
}
