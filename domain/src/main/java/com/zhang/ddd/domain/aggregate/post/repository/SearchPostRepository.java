package com.zhang.ddd.domain.aggregate.post.repository;

import java.util.List;
import com.zhang.ddd.domain.aggregate.post.entity.SearchItem;

public interface SearchPostRepository {

    void save(SearchItem item);

    void update(SearchItem item);

    List<SearchItem> searchByKeyWord(PostSearchQuery searchQuery);

}
