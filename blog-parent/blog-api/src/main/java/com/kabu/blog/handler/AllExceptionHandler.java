package com.kabu.blog.handler;

import com.kabu.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AllExceptionHandler {
    //进行异常处理,处理exception.class的类
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回json数据
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"系统异常");
    }
}
