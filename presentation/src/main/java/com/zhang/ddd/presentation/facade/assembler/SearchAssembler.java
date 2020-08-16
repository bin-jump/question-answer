package com.zhang.ddd.presentation.facade.assembler;

import java.util.List;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.post.entity.SearchItem;
import com.zhang.ddd.presentation.facade.dto.post.SearchDto;

public class SearchAssembler {

    public static SearchDto toDTO(SearchItem searchItem) {
        if (searchItem == null) {
            return null;
        }
        SearchDto searchDto = SearchDto.builder()
                .id(searchItem.getId())
                .parentId(searchItem.getParentId())
                .score(searchItem.getScore())
                .title(searchItem.getTitle())
                .body(searchItem.getBody())
                .authorId(searchItem.getAuthorId())
                .searchType(searchItem.getItemType().name())
                .created(searchItem.getCreated().getTime())
                .build();

        return searchDto;
    }

    public static List<SearchDto> toDTOs(List<SearchItem> searchItems) {

        return searchItems.stream().map(SearchAssembler::toDTO)
                .collect(Collectors.toList());
    }
}
