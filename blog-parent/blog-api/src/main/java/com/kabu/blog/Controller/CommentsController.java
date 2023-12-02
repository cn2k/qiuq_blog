package com.kabu.blog.Controller;

import com.kabu.blog.Service.commentsService;
import com.kabu.blog.vo.CommentParam;
import com.kabu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    private commentsService commentsservice;


        //    /comments/article/{id}
    @GetMapping("/article/{id}")
    public Result comments(@PathVariable("id") Long id){
        return commentsservice.commentsByArticleId(id);
    }

    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsservice.comment(commentParam);
    }
}
