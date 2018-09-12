package com.rfxdevelop.stockweb.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data @Builder
public class DbTable {
    private String table_name;
    private Map<String,DbColumn> columns;
}
