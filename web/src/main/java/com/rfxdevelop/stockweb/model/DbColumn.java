package com.rfxdevelop.stockweb.model;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DbColumn {
    private String column_name;
    private String column_comment;
    private String column_type;
    private String data_type;
    private String is_nullable;
    private boolean is_key;
}
