package com.kabu.blog.Controller;

import com.kabu.blog.Service.LoginService;
import com.kabu.blog.vo.Result;
import com.kabu.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParams loginParam){

        return loginService.login(loginParam);
    }
}
