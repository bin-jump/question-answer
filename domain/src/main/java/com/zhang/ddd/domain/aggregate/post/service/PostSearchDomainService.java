package com.zhang.ddd.domain.aggregate.post.service;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.SearchItem;
import com.zhang.ddd.domain.aggregate.post.repository.SearchPostRepository;
import com.zhang.ddd.domain.aggregate.post.repository.PostSearchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostSearchDomainService {

    @Autowired
    SearchPostRepository searchPostRepository;

    public List<SearchItem> search(String keyWord, Float cursorScore, Long cursorId, int size) {
        PostSearchQuery searchQuery = PostSearchQuery.builder()
                .keyWord(keyWord)
                .cursorScore(cursorScore)
                .cursorId(cursorId)
                .size(size)
                .build();

        List<SearchItem> searchItems = searchPostRepository.searchByKeyWord(searchQuery);

        return searchItems;
    }
}
