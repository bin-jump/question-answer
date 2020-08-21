package com.zhang.ddd.domain.aggregate.user.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeAvatarCommand {

    private String userId;

    private String fileName;

    private long fileSize;

    InputStream stream;
}
