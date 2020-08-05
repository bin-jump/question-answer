package com.zhang.ddd.presentation.facade.assembler;

import com.zhang.ddd.domain.aggregate.post.entity.Tag;
import com.zhang.ddd.presentation.facade.dto.post.TagDto;

public class TagAssembler {

    public static TagDto toDTO(Tag tag) {

        TagDto tagDto = TagDto.builder()
                .label(tag.getLabel())
                .build();

        return tagDto;
    }
}
