package com.rfxdevelop.stockweb.controller;

import com.rfxdevelop.stockweb.model.BaseData;
import com.rfxdevelop.stockweb.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping(value = "/baseData")
public class BaseDataController {

    @Autowired
    private BaseData baseData;

    @RequestMapping("/get")
    public Result get(HttpServletRequest request) throws Exception {
        Result result = Result.successResult();
        result.put("baseData",baseData);
        return result;
    }
    @RequestMapping("/getByField")
    public Result getByField(HttpServletRequest request,@RequestParam String fieldName) throws Exception {
        Result result = Result.successResult();
        Field field = baseData.getClass().getDeclaredField(fieldName);
        if(field!=null){
            field.setAccessible(true);
            result.put("value",field.get(baseData));
        }
        return result;
    }
}
