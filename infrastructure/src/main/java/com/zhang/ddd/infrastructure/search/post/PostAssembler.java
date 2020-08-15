package com.zhang.ddd.infrastructure.search.post;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.zhang.ddd.domain.aggregate.post.entity.SearchItem;
import com.zhang.ddd.domain.aggregate.post.entity.valueobject.SearchItemType;
import org.omg.PortableServer.POA;
import org.springframework.beans.BeanUtils;

public class PostAssembler {

    public static SearchItem toDO(Post post) {

        SearchItem item = new SearchItem();
        BeanUtils.copyProperties(post, item);
        item.setItemType(SearchItemType.valueOf(post.getItemType()));
        item.setCreated(Date.from(post.getCreated()));

        return item;
    }

    public static List<SearchItem> toDOs(List<Post> posts) {

        return posts.stream().map(PostAssembler::toDO).collect(Collectors.toList());
    }

    public static Post toPO(SearchItem searchItem) {

        Post post = new Post();
        BeanUtils.copyProperties(searchItem, post);
        post.setItemType(searchItem.getItemType().name());
        post.setCreated(searchItem.getCreated().toInstant());

        return post;
    }
}
