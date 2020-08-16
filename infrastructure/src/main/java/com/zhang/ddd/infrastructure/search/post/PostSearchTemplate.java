package com.zhang.ddd.infrastructure.search.post;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.SearchDocumentResponse;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * this class is for search with custom SearchRequest
 */
@Slf4j
public class PostSearchTemplate extends ElasticsearchRestTemplate {

    RestHighLevelClient esClient;
    public PostSearchTemplate(RestHighLevelClient client) {
        super(client);
        esClient = client;
    }

    public List<Post> doSearch(SearchRequest searchRequest) {

        SearchResponse response = null;
        try {
            response = this.esClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("Search error: " + e);
        }
        IndexCoordinates index = getIndexCoordinatesFor(Post.class);
        SearchDocumentResponseCallback<org.springframework.data.elasticsearch.core.SearchHits<Post>> callback =
                new ReadSearchDocumentResponseCallback(Post.class, index);
        SearchHits<Post> postHits = callback.doWith(SearchDocumentResponse.from(response));
        List<Post> posts = postHits.getSearchHits().stream()
                .map(SearchHit::getContent).collect(Collectors.toList());

        Map<String, Post> idMapping = posts.stream().collect(Collectors.toMap(Post::getId, e -> e));

        for (org.elasticsearch.search.SearchHit hit : response.getHits()) {
            idMapping.get(hit.getId()).setScore(hit.getScore());
        }

        return posts;
    }
}
