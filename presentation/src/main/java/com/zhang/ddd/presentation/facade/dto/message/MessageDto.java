package com.zhang.ddd.presentation.facade.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {

    private Long id;

    private String body;

    private Long fromId;

    private boolean fromMe;

    private long created;
}
