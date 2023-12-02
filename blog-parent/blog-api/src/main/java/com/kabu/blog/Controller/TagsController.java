package com.kabu.blog.Controller;

import com.kabu.blog.Service.TagService;
import com.kabu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
public class TagsController {
    @Autowired
    private TagService tagService;
    @GetMapping("hot")
    public Result hot(){
        int limit=6;
        return tagService.hots(limit);
    }

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }
    //查询所有标签
    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    //根据id查询文章
    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }
}
