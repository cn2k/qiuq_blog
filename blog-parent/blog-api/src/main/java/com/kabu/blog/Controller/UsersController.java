package com.kabu.blog.Controller;

import com.kabu.blog.Service.SysUserService;
import com.kabu.blog.dao.pojo.SysUser;
import com.kabu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UsersController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){

        return sysUserService.findUserBytoken(token);
    }
}
