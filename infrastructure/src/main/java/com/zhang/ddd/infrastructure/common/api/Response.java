package com.zhang.ddd.infrastructure.common.api;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {

    private Status status;
    private String msg;
    private Object data;

    public static Response ok(){
        return Response.builder().status(Status.SUCCESS).build();
    }

    public static Response ok(Object data){
        return Response.builder().status(Status.SUCCESS).data(data).build();
    }

    public static Response okPagingAfter(List data, String after, int pageSize){
        after = data.size() >= pageSize ? after : null;
        PagingData pageData = PagingData.builder()
                .children(data)
                .after(after)
                .dist(data.size())
                .build();
        return Response.builder().status(Status.SUCCESS).data(pageData).build();
    }

    public static Response okPagingBefore(List data, String before, int pageSize){
        PagingData pageData = PagingData.builder()
                .children(data)
                .before(before)
                .dist(data.size())
                .build();
        return Response.builder().status(Status.SUCCESS).data(pageData).build();
    }

    public static Response failed(String msg){
        return Response.builder().status(Status.FAILED).msg(msg).build();
    }

    public enum Status{
        SUCCESS, FAILED
    }
}

