package com.zhang.ddd.presentation.facade.assembler;

import com.zhang.ddd.domain.aggregate.post.entity.Comment;
import com.zhang.ddd.presentation.facade.dto.post.CommentDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;

public class CommentAssembler {

    public static CommentDto toDTO(Comment comment, UserDto userDto) {
        if (comment == null) {
            return null;
        }
        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())

                .authorId(comment.getAuthorId())
                .body(comment.getBody())
                .created(comment.getCreated().getTime())
                .resourceId(comment.getResourceId())
                .resourceType(comment.getResourceType().name())
                .build();

        if (userDto != null) {
            commentDto.setAuthor(userDto);
        }

        return commentDto;
    }
}
