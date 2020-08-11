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
public class ChatPO {

    private long id;

    private long version;

    private long chatterYouId;

    private long chatterMeId;

    private Long topMessageId;

    private String topBody;

    private Date topMessageCreated;

    private int youUnreadCount;

    private int meUnreadCount;

    private Date created;


}
