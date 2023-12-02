package com.kabu.blog.Service;


import com.kabu.blog.vo.CommentParam;
import com.kabu.blog.vo.Result;

public interface commentsService {
    Result comment(CommentParam commentParam);

    /**
     * 根据文章id查询评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);
}
