package com.zhang.ddd.presentation.facade;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.post.entity.SearchItem;
import com.zhang.ddd.domain.aggregate.post.service.PostSearchDomainService;
import com.zhang.ddd.domain.aggregate.user.repository.UserRepository;
import com.zhang.ddd.presentation.facade.assembler.SearchAssembler;
import com.zhang.ddd.presentation.facade.assembler.UserAssembler;
import com.zhang.ddd.presentation.facade.dto.post.SearchDto;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceFacade {

    @Autowired
    PostSearchDomainService searchDomainService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FacadeHelper helper;

    public List<SearchDto> searchPost(String searchKey, Float cursorScore, String cursorId, int size) {
        List<SearchItem> searchItems = searchDomainService.search(searchKey, cursorScore, cursorId, size);
        List<SearchDto> res = SearchAssembler.toDTOs(searchItems);
        fillUser(res);
        return res;
    }

    private void fillUser(List<SearchDto> searchDtos) {
        Map<String, UserDto> users = userRepository
                .findByIds(searchDtos.stream().map(SearchDto::getAuthorId).collect(Collectors.toList()))
                .stream().map(UserAssembler::toDTO).collect(Collectors.toMap(UserDto::getId, e -> e));

        searchDtos.forEach(e -> e.setAuthor(users.get(e.getAuthorId())));
    }
}
