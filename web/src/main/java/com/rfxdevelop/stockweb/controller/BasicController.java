package com.rfxdevelop.stockweb.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.renfxdevelop.utils.original.request.RequestParamUtil;
import com.rfxdevelop.stockweb.dao.dynamic.IMapper;
import com.rfxdevelop.stockweb.dao.dynamic.exception.MapperParamException;
import com.rfxdevelop.stockweb.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.stream.Stream;


@Slf4j
@RestController
@RequestMapping(value = "/basic")
public class BasicController {

    @Autowired(required = false)
    private IMapper iMapper;


    @RequestMapping("/{method}")
    public Result entrance(HttpServletRequest request, @PathVariable("method") String methodStr, @RequestParam String tableName) throws Exception {
        Result result = Result.successResult();
        Map<String, String> params = RequestParamUtil.getParams(request);
        String[] methods = methodStr.split("-");
        for(String method:methods){
            Method mapperMethod = Stream.of(IMapper.class.getDeclaredMethods())
                    .filter(m -> m.getName().equals(method))
                    .findAny()
                    .orElseThrow(() -> new MapperParamException("method not found"));
            Object[] methodParams = getMethodParams(mapperMethod,params);
            Object invoke = mapperMethod.invoke(iMapper, methodParams);
            result.put(method,invoke);
        }
        return result;
    }


    /**
     * 将请求参数转为Mapper参数
     * @param mapperMethod Mapper执行的方法
     * @param params       Http请求参数
     * @return
     */
    private Object[]  getMethodParams(Method mapperMethod,Map<String, String> params){
        Object[] methodParams = new Object[mapperMethod.getParameterCount()];
        final int[] i = {0};
        Stream.of(mapperMethod.getParameterAnnotations()).forEach(s->{
            Stream.of(s).filter(a->a.annotationType().equals(Param.class))
                    .map(a->(Param)a)
                    .forEach(p->{
                        String paramName = p.value();
                        String paramValue = params.get(paramName);
                        try{
                            methodParams[i[0]] = JSONUtils.parse(paramValue);
                        }catch (Exception e){
                            methodParams[i[0]] = paramValue;
                        }
                        i[0]++;
                    });
        });
        return methodParams;
    }
}
