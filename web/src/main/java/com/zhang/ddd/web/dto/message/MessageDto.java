package com.zhang.ddd.web.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {

    private String id;

    private String body;

    private String fromId;

    private String toId;

    private boolean fromMe;

    private long created;
}
