package com.rfxdevelop.stockweb.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Data
public class BaseData {
    private Map<String,DbTable> tableMap;
    private Map<String,String> tableShowMap;
    private List<Map<String, Object>> basicStocks;
}
