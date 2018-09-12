## 项目简述
Spring Boot + MyBatis + Mysql  
映射表的增、删、改、查基础功能，**本项目将会被SQL注入**

## 需求
Python从网络抓取数据到数据库，部分人不需要特别复杂的管理系统来管理这些数据，很可能仅仅需要一个比数据库更友好的界面来查看数据。  

我最近炒股，用tushare抓取了数据，在获得这些数据以后，我需要一个管理系统，做简单的管理，又不想用代码生成器生成一大堆模板代码。  
所以，我想把所有耦合在一起，只要一个入口访问所有的表，然后把表的数据映射到前端。
> 前端参考https://github.com/renfx/renfx-basic-vue

## 功能展示
接口请求数据格式
* tableName，表名，必须，仅支持单个
* whereList，过滤条件
* columns，列名
* pageQuery，分页、排序数据
* modifyMap，修改数据
* insertMap，新增数据 

接口返回数据格式示例：
```
{
  "code": 0,
  "msg": "成功",
  "success": true,
  "map": {
  }
}
```
### 1. 查询
POST：/basic/select、/basic/select-selectCount  
支持参数：
tableName
whereList
pageQuery
columns

### 2. 删除
POST：/basic/delete  
支持参数：tableName，whereList

### 3. 修改
POST：/basic/update  
支持参数：tableName，whereList，modifyMap

### 4. 新增
POST：/basic/insert  
支持参数：tableName，whereList，insertMap

### 5. 导出
window.location：/simple/table/export?&whereList=%5B%7B%22key%22%3A%22index%22%2C%22separator%22%3A%22%3D%22%2C%22value%22%3A%2211%22%7D%5D&tableName=hs300s  
支持参数：tableName，whereList，pageQuery，columns

### 6. 表基础信息
POST：/baseData/get  
tableMap：映射表列的备注、是否主键、列类型等。
tableShowMap：表名对应的显示名称


## 配置，启动项目
配置 /resource/application.yml
tableSchema：数据库名
spring.datasource.druid.url：数据库连接地址  
> 数据库需要information_schema的访问权限，不然初始化表数据将会出错

配置 安装编译器插件lombok
> 若安装插件后编译依然报错，在 idea 中，File -> Settings -> Build.Execution,Deployment -> Annotation Processors 中的 Enable annotation processing 打勾。同时确保File -> Settings -> Build.Execution,Deployment -> Java Complier 中的 Project bytecode version 选中JDK的版本

然后从com.rfxdevelop.basicmvc.DevelopApplication启动项目
