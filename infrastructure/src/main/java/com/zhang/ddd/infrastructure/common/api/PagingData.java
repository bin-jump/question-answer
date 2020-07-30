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
public class PagingData {

    private Object before;

    private Object after;

    private List children;

    private int dist;
}
