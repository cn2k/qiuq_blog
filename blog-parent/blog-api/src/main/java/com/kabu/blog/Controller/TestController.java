package com.kabu.blog.Controller;

import com.kabu.blog.dao.pojo.SysUser;
import com.kabu.blog.utils.UserThreadLocal;
import com.kabu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser= UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}