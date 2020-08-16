package com.zhang.ddd.infrastructure.search.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oracle.jrockit.jfr.ValueDefinition;
import com.zhang.ddd.domain.aggregate.post.entity.valueobject.SearchItemType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(indexName = "post", type = "docs", shards = 1, replicas = 0)
public class Post {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String itemType;

    @Field(type = FieldType.Keyword)
    private String authorId;

    @Field(type = FieldType.Keyword)
    private String parentId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String body;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Instant created;

    private Float score;

//    @Field(type = FieldType.Date, store = true, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "GMT+09:00")
//    private Date created;


}
