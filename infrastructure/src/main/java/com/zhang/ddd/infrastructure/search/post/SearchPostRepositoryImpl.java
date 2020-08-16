package com.zhang.ddd.infrastructure.search.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhang.ddd.domain.aggregate.post.entity.SearchItem;
import com.zhang.ddd.domain.aggregate.post.entity.valueobject.SearchItemType;
import com.zhang.ddd.domain.aggregate.post.repository.SearchPostRepository;
import com.zhang.ddd.domain.aggregate.post.repository.PostSearchQuery;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SearchPostRepositoryImpl implements SearchPostRepository {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ElasticsearchRestTemplate searchTemplate;

    @Autowired
    private RestHighLevelClient esClient;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void save(SearchItem item) {
        Post post = PostAssembler.toPO(item);
        postRepository.save(post);
    }

    @Override
    public void update(SearchItem item) {
        Post post = PostAssembler.toPO(item);
        postRepository.save(post);
    }

    @Override
    public List<SearchItem> searchByKeyWord(PostSearchQuery searchQuery) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        QueryBuilder basicQuery = this.makeBasicQuery(searchQuery);
        queryBuilder.withQuery(basicQuery);
        //SearchHits<Post> postHits = searchTemplate.search(queryBuilder.build(), Post.class);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(basicQuery)
                .size(searchQuery.getSize())
                .sort("_score", SortOrder.DESC)
                .sort("_id", SortOrder.ASC);
        sourceBuilder.highlighter(new HighlightBuilder()
                .preTags("<em style='background:yellow'>")
                .postTags("</em>")
                .field("title")
                .field("body"));

        if (searchQuery.hasCursor()) {
            sourceBuilder.searchAfter(new Object[]{searchQuery.getCursorScore(),
                    searchQuery.getCursorId()});
        }
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("post");
        searchRequest.source(sourceBuilder);

        PostSearchTemplate template = new PostSearchTemplate(esClient);
        List<Post> posts = template.doSearch(searchRequest);

        List<SearchItem> res = PostAssembler.toDOs(posts);
        return res;
    }

    private QueryBuilder makeBasicQuery(PostSearchQuery searchQuery) {
        BoolQueryBuilder questionQuery = QueryBuilders.boolQuery();
        questionQuery.must(QueryBuilders.termQuery("itemType", SearchItemType.QUESTION.name()));
        questionQuery.must(QueryBuilders.matchQuery("title", searchQuery.getKeyWord()));

        BoolQueryBuilder answerQuery = QueryBuilders.boolQuery();
        answerQuery.must(QueryBuilders.termQuery("itemType", SearchItemType.ANSWER.name()));
        answerQuery.must(QueryBuilders.matchQuery("body", searchQuery.getKeyWord()));

        BoolQueryBuilder topQueryBuilder = new BoolQueryBuilder();
        topQueryBuilder.should(questionQuery);
        topQueryBuilder.should(answerQuery);

        return topQueryBuilder;
    }
}
