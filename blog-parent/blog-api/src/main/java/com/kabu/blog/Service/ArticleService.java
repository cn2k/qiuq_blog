package com.kabu.blog.Service;

import com.kabu.blog.vo.Result;
import com.kabu.blog.vo.ArticleVo;
import com.kabu.blog.vo.params.ArticleParam;
import com.kabu.blog.vo.params.PageParams;

public interface ArticleService {
    //分页查询文章列表
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    Result newArticles(int limit);

    /**
     * 根据articleid来查询文章内容
     * @param id
     * @return
     */
    Result findByArticleId(Long id);
    //发布文章
    Result publish(ArticleParam articleParam);
}
