package com.kabu.blog.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kabu.blog.Service.SysUserService;
import com.kabu.blog.Service.commentsService;
import com.kabu.blog.dao.mapper.ArticleMapper;
import com.kabu.blog.dao.mapper.CommentMapper;
import com.kabu.blog.dao.pojo.Article;
import com.kabu.blog.dao.pojo.Comment;
import com.kabu.blog.dao.pojo.SysUser;
import com.kabu.blog.utils.UserThreadLocal;
import com.kabu.blog.vo.CommentParam;
import com.kabu.blog.vo.CommentVo;
import com.kabu.blog.vo.Result;
import com.kabu.blog.vo.UserVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class commentsServiceimpl implements commentsService {
    @Autowired
    private CommentMapper commentmapper;
    @Autowired
    private SysUserService userService;
    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public Result commentsByArticleId(Long id) {
        //1.根据文章id查询评论列表,从comment列表查询
        //2.根据作者的id查询作者的信息
        //3.判断如果level=1 查询是否有子评论
        //4.如果有则根据评论id查询
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        //根据文章id进行查询
        queryWrapper.eq(Comment::getArticleId,id);
        queryWrapper.eq(Comment::getLevel,1);
        List<Comment> comments = commentmapper.selectList(queryWrapper);
        //update set comment_counts=(select count(*) from ms_comment)
        List<CommentVo> commentVoList=copyList(comments);
        int commentCount = comments.size(); // 获取评论数量
        // 更新文章表中的评论数量
        Article article = new Article();
        article.setId(id); // 设置文章的ID
        article.setCommentCounts(commentCount); // 设置评论数量
        articleMapper.updateById(article); // 执行更新操作
        return Result.success(commentVoList);
    }

    @Override
    public Result comment(CommentParam commentParam) {
        //添加评论
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        Long articleId = commentParam.getArticleId();
        comment.setArticleId(articleId);
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentmapper.insert(comment);
        return Result.success(null);
    }



    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList=new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo=new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);
        //时间格式化
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //作者信息
        Long authorId=comment.getAuthorId();
        UserVo userVo=this.userService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = comment.getLevel();
        if(1==level){
            Long id = comment.getId();
            List<CommentVo> commentVoList=findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        //给谁评论
        if(level>1){
            Long toUid = comment.getToUid();
            UserVo toUserVo = this.userService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        return copyList(commentmapper.selectList(queryWrapper));
    }
}
