package com.rfxdevelop.stockweb.dao.dynamic;

import com.rfxdevelop.stockweb.dao.dynamic.exception.MapperParamException;
import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

@Deprecated
public class MapperSql {
    public String getSelectSql(@Param("whereList")List<Rule> whereList,@Param("table_name") String tableName,@Param("columns") List<String> columns){

        return new SQL(){
            {
                if(columns==null || columns.size()==0){
                    SELECT("*");
                }else{
                    String[] columnsArr = new String[columns.size()];
                    columns.toArray(columnsArr);
                    for(int i=0,n=columnsArr.length;i<n;i++){
                        columnsArr[i] = markKey(columnsArr[i]);
                    }
                    SELECT(columnsArr);
                }
                SQL sql = FROM(tableName);
                defaultWhere(whereList, sql);
            }
        }.toString();
    }

    public String getModifySql(@Param("whereList")List<Rule> whereList, @Param("table_name") String tableName, @Param("modifyMap")Map<String, Object> modifyMap){
        return new SQL(){
            {
                SQL sql = UPDATE(tableName);
                modifyMap.keySet().stream().filter(key->key!=null).forEach(key->SET(markKey(key)+"=#{modifyMap."+key+"}"));
                defaultWhere(whereList, sql);
            }
        }.toString();
    }
    public String getInsertSql(@Param("table_name")String tableName, @Param("insertMap")Map<String, Object> insertMap){
        return new SQL(){
            {
                SQL sql = INSERT_INTO(tableName);
                insertMap.keySet().stream().filter(key->key!=null).forEach(key->{
                    INTO_COLUMNS(markKey(key));
                    INTO_VALUES("#{insertMap."+key+"}");
                });
            }
        }.toString();
    }
    public String getDeleteSql(@Param("whereList")List<Rule> whereList,@Param("table_name")String tableName){
        return new SQL(){
            {
                SQL sql = DELETE_FROM(tableName);
                defaultWhere(whereList,sql);
            }
        }.toString();
    }

    /**
     * Where条件
     * @param whereList
     * @param sql
     */
    private void defaultWhere(List<Rule> whereList,SQL sql){
        if(whereList==null) return;
        for(int i=0,n=whereList.size();i<n;i++){
            Object item = whereList.get(i);
            Rule r;
            if(item instanceof Rule){
                r = whereList.get(i);
            }else if(item instanceof Map){
                Map<String,Object> map = (Map) item;
                r = new Rule.RuleBuilder()
                        .key(MapUtils.getString(map,"key"))
                        .separator(MapUtils.getString(map,"separator"))
                        .value(MapUtils.getString(map,"value")).build();
            }else{
                throw new MapperParamException("not support class type:"+Object.class.getName());
            }
            if(r.getKey()!=null && r.getSeparator()!=null &&r.getValue()!=null){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(markKey(r.getKey())).append(r.getSeparator())
                        .append("#{whereList[").append(i).append("].").append("value").append("}");
                sql.WHERE(stringBuilder.toString());
            }
        }
    }

    /**
     *
     * 避免数据库字段为关键字
     * @param key
     * @return
     */
    private String markKey(String key){
        return "`"+key+"`";
    }

}
