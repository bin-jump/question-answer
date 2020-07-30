package com.zhang.ddd.web.dto.comment;

import com.zhang.ddd.web.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private String id;


    private String resourceId;


    private String resourceType;

    @NotBlank
    private String body;

    private long created;

    private String authorId;

    private UserDto author;

}
