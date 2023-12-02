package com.kabu.blog.Controller;

import com.kabu.blog.Service.ArticleService;
import com.kabu.blog.common.aop.LogAnnotation;
import com.kabu.blog.common.cache.Cache;
import com.kabu.blog.vo.ArticleVo;
import com.kabu.blog.vo.Result;
import com.kabu.blog.vo.params.ArticleParam;
import com.kabu.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//json数据进行交互
@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */
    //对获取文章操作添加日志
    @LogAnnotation(module = "文章",operation = "获取文章列表")
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }
//    最热文章
    //添加缓存操作
    @PostMapping("hot")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    //最新文章
    @PostMapping("new")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }
    /**
     * 文章详情
     */
    @PostMapping("view/{id}")
    public Result findByArticleId(@PathVariable("id") Long id){
        return articleService.findByArticleId(id);
    }

    //发布文章
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        Result publishArticle = articleService.publish(articleParam);
        return publishArticle;
    }
    @PostMapping("{id}")
    public Result articleById(@PathVariable("id") Long articleId){
        return articleService.findByArticleId(articleId);
    }
}
