package com.zhang.ddd.presentation.facade;

import com.zhang.ddd.application.service.UserApplicationService;
import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.presentation.facade.assembler.UserAssembler;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceFacade {

    @Autowired
    UserApplicationService userApplicationService;

    @Autowired
    UserRepository userRepository;

    public UserDto create(UserDto userDto) {

        User user = userApplicationService.create(userDto.getName(), userDto.getPassword(), userDto.getEmail());
        return UserAssembler.toDTO(user);
    }

    public UserDto findByName(String name) {
        User user = userRepository.findByName(name);
        return UserAssembler.toDTO(user);
    }
}
