package com.zhang.ddd.infrastructure.persistence.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagePO {

    private long id;

    private long chatId;

    private String body;

    private Date created;

    private long fromId;

}
