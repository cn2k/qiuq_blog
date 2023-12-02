package com.kabu.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//参数返回值
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private boolean success;

    private int code;

    private String msg;

    private Object data;
    //返回成功
    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }
//返回失败
    public static Result fail(int code,String msg){
        return new Result(false,code,msg,null);
    }
}
