package com.rfxdevelop.stockweb.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.renfxdevelop.utils.copy.common.DateConvertUtils;
import com.renfxdevelop.utils.original.excel.Excel2007Utils;
import com.renfxdevelop.utils.original.request.RequestParamUtil;
import com.rfxdevelop.stockweb.dao.dynamic.IMapper;
import com.rfxdevelop.stockweb.model.BaseData;
import com.rfxdevelop.stockweb.model.DbColumn;
import com.rfxdevelop.stockweb.model.DbTable;
import com.rfxdevelop.stockweb.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping(value = "/simple/table")
public class TableController {

    @Autowired(required = false)
    private IMapper iMapper;
    @Autowired
    private BaseData baseData;

    @RequestMapping("/get")
    public Result baseData(HttpServletRequest request, @RequestParam String tableName) throws Exception {
        DbTable dbTable = baseData.getTableMap().get(tableName);
        Result result = Result.successResult();
        result.put("dbTable",dbTable);
        return result;
    }

    @RequestMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response, @RequestParam String tableName) throws Exception {
        Map<String, String> params = RequestParamUtil.getParams(request);
        String whereList = params.get("whereList");
        String columns = params.get("columns");
        String pageQuery = params.get("pageQuery");
        List<Map> whereListMap = null;
        List<String> columnList = null;
        Map<String,Object> pageQueryMap = null;
        try{
            whereListMap = (List<Map>) JSONUtils.parse(whereList);
            columnList = (List<String>) JSONUtils.parse(columns);
            pageQueryMap = (Map<String, Object>) JSONUtils.parse(pageQuery);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<Map<String, Object>> list = iMapper.select(whereListMap, tableName, columnList, pageQueryMap);

        List<String> cols = new ArrayList<>();
        if(!list.isEmpty()){
            Map<String, DbColumn> tableColumns = baseData.getTableMap().get(tableName).getColumns();
            Map<String, Object> stringObjectMap = list.get(0);
            stringObjectMap.keySet().stream().forEach(column->{
                DbColumn dbColumn = tableColumns.get(column);
                cols.add(dbColumn.getColumn_comment());
            });
        }
        String fileName = tableName+ DateConvertUtils.format(new Date(),DateConvertUtils.FORMAT_NOT_BR);
        Excel2007Utils.install().exportByList(response,fileName,tableName,list,cols);
    }
}
