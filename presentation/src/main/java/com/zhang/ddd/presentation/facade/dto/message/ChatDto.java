package com.zhang.ddd.presentation.facade.dto.message;

import java.util.List;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import lombok.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDto {

    private Long id;

    private Long withId;

    private UserDto withUser;

    private String coverText;

    private Long coverId;

    private long lastTime;

    private int unreadCount;

    private List<MessageDto> messages = new ArrayList<>();

}
