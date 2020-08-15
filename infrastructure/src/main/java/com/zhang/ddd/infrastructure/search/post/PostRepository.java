package com.zhang.ddd.infrastructure.search.post;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

public interface PostRepository extends ElasticsearchRepository<Post, String> {
}
