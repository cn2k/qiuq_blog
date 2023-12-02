package com.kabu.blog.Controller;

import com.kabu.blog.Service.VisitersService;
import com.kabu.blog.dao.pojo.Visiters;
import com.kabu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("visiters")
public class VisitersController {
    @Autowired
    private VisitersService visitersservice;
    //添加评论
    @PostMapping("plusadd")
    public Result plusadd(@RequestBody Visiters visiters){
        return visitersservice.add(visiters);
    }
    //查询留言
    @GetMapping
    public Result select(){
        return visitersservice.select();
    }
}
