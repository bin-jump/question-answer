package com.zhang.ddd.presentation.facade.dto.post;

import com.zhang.ddd.presentation.facade.dto.user.UserDto;
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

    private Long id;


    private Long resourceId;


    private String resourceType;

    @NotBlank
    private String body;

    private long created;

    private Long authorId;

    private UserDto author;

}
