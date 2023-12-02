package com.kabu.blog.Controller;

import com.kabu.blog.Service.CategoryService;
import com.kabu.blog.vo.CategoryVo;
import com.kabu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result listCategory() {
        return categoryService.findAll();
    }
    //查询所有分类
    @GetMapping("detail")
    public Result categoriesDetail(){
        return categoryService.findAllDetail();
    }
    //根据文章id查询对应文章
    @GetMapping("detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") Long id){
        return categoryService.categoriesDetailById(id);
    }

}
