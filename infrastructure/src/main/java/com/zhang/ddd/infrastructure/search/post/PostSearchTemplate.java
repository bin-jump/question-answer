package com.zhang.ddd.infrastructure.search.post;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.SearchDocumentResponse;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    private final String POST_TITLE = "title";

    private final String POST_BODY = "body";

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

        Map<Long, Post> idMapping = posts.stream().collect(Collectors.toMap(Post::getId, e -> e));

        for (org.elasticsearch.search.SearchHit hit : response.getHits()) {
            Post curPost = idMapping.get(Long.parseLong(hit.getId()));
            curPost.setScore(hit.getScore());
            List<String> titleHeights = getHeight(hit, POST_TITLE);
            List<String> bodyHeights = getHeight(hit, POST_BODY);
            if (titleHeights.size() > 0) {
                curPost.setTitle(String.join("", titleHeights));
            }
            if (bodyHeights.size() > 0) {
                curPost.setBody("..." + String.join("", bodyHeights) + "...");
            }

        }
        return posts;
    }

    private List<String> getHeight(org.elasticsearch.search.SearchHit hit, String fieldName) {
        HighlightField highlightField = hit.getHighlightFields().get(fieldName);
        if (highlightField == null) {
            return new ArrayList<>();
        }

        List<String> res = Arrays.stream(highlightField.fragments()).map(Text::string)
                .collect(Collectors.toList());

        return res;
    }
}
