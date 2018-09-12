package com.rfxdevelop.stockweb;

import com.rfxdevelop.stockweb.enumeration.ResultCode;
import com.rfxdevelop.stockweb.model.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CommonExceptionHandler
{
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handler(Exception e){
        Result result = Result.exceptionResult();
        result.setMsg(e.getMessage()==null? ResultCode.EXCETION.getDescription():e.getMessage());
        e.printStackTrace();
        return result;
    }
}
