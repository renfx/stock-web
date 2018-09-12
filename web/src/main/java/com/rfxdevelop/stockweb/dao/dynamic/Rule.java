package com.rfxdevelop.stockweb.dao.dynamic;

import lombok.Builder;
import lombok.Data;

@Data@Builder
public class Rule {
    private String key;
    private String separator;
    private Object value;
}
