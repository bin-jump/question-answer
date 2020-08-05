package com.zhang.ddd.presentation.facade.assembler;

import com.zhang.ddd.domain.aggregate.post.entity.Answer;
import com.zhang.ddd.presentation.facade.dto.post.AnswerDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;

public class AnswerAssembler {

    public static AnswerDto toDTO(Answer answer, UserDto userDto, boolean upvoted, boolean downvoted) {
        if (answer == null) {
            return null;
        }
        AnswerDto answerDTO = AnswerDto.builder()
                .id(answer.getId())

                .authorId(answer.getAuthorId())
                .body(answer.getBody())
                .created(answer.getCreated().getTime())
                .commentCount(answer.getCommentCount())
                .parentId(answer.getParentId())
                .upvoteCount(answer.getUpvoteCount())
                .upvoted(upvoted)
                .downvoted(downvoted)
                .build();

        if (userDto != null) {
            answerDTO.setAuthor(userDto);
        }

        return answerDTO;
    }
}
