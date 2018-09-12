package com.rfxdevelop.stockweb.model;

import com.alibaba.druid.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class TableShow {
    public static TableShow install(){
        return new TableShow();
    }
    public String getTableShow(String tableName){
        String tableShow = tableShowMap().get(tableName);
        if(StringUtils.isEmpty(tableName)) tableShow = tableName;
        return tableShow;
    }
    public Map<String,String> tableShowMap(){
        Map<String,String> map = new HashMap<>();
        map.put("industry_classified","行业分类");
        map.put("concept_classified","概念分类");
        map.put("area_classified","地域分类");
        map.put("sme_classified","中小板");
        map.put("gem_classified","创业板");
        map.put("st_classified","风险警示板");
        map.put("hs300s","沪深300");
        map.put("sz50s","上证50");
        map.put("zz500s","中证500");
        map.put("stock_basics","股票列表");
        map.put("new_stocks","新股数据");
        map.put("latest_news","实时新闻");
        map.put("his_data","历史数据");
        return map;
    }
}
