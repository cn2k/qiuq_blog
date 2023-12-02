package com.kabu.blog.Controller;

import com.kabu.blog.Service.LoginService;
import com.kabu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;


//    public Result logout(@RequestHeader("Authorization") String TOKEN){
    @GetMapping
    public Result logout(@RequestHeader("oauth-token") String TOKEN){
        return loginService.logout(TOKEN);
    }
}
