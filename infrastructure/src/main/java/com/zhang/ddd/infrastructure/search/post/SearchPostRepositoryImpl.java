package com.zhang.ddd.infrastructure.search.post;

import com.zhang.ddd.domain.aggregate.post.entity.SearchItem;
import com.zhang.ddd.domain.aggregate.post.entity.valueobject.SearchItemType;
import com.zhang.ddd.domain.aggregate.post.repository.SearchPostRepository;
import com.zhang.ddd.domain.aggregate.post.repository.PostSearchQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        SearchHits<Post> postHits = searchTemplate.search(queryBuilder.build(), Post.class);
        List<Post> posts = postHits.getSearchHits().stream()
                .map(SearchHit::getContent).collect(Collectors.toList());

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
