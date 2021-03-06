package com.zhang.ddd.infrastructure.search.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oracle.jrockit.jfr.ValueDefinition;
import com.zhang.ddd.domain.aggregate.post.entity.valueobject.SearchItemType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(indexName = "post", shards = 1, replicas = 0, createIndex=true)
@Setting(settingPath = "/settings/settings.json")
public class Post {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String itemType;

    @Field(type = FieldType.Long)
    private Long authorId;

    @Field(type = FieldType.Long)
    private Long parentId;

    @Field(type = FieldType.Text, analyzer = "post_content_analyzer")
    private String title;

    @Field(type = FieldType.Text, analyzer = "post_content_analyzer")
    private String body;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Instant created;

    private Float score;

//    @Field(type = FieldType.Date, store = true, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "GMT+09:00")
//    private Date created;


}
