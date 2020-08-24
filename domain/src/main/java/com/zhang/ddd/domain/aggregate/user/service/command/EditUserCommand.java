package com.zhang.ddd.domain.aggregate.user.service.command;

import com.zhang.ddd.domain.aggregate.user.entity.valueobject.UserGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditUserCommand {

    private Long id;

    private String email;

    private int age;

    private UserGender gender;

    private String description;
}
