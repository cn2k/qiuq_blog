package com.kabu.blog.Service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.kabu.blog.dao.mapper.ArticleMapper;
import com.kabu.blog.dao.pojo.Article;
import com.kabu.blog.dao.pojo.ArticleBody;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ThreadService {
    //此操作在线程池中进行操作,执行,不会影响所有主线程
    //异步任务
    /**
     * 这段代码主要是用于更新文章的浏览量（viewCounts）。
     * 首先，通过 article.getViewCounts() 获取当前文章的浏览量，并将其存储在 viewCounts 变量中。
     * 接着，创建一个新的 Article 对象 articleUpdate，并将其浏览量设置为 viewCounts+1。
     * 然后，创建一个 LambdaUpdateWrapper<Article> 对象 updateWrapper，用于构建更新文章的条件。通过 updateWrapper.eq(Article::getId,article.getId()) 指定更新条件为文章的 id 值等于当前文章的 id 值。
     * 为了保证多线程环境下的线程安全，使用 updateWrapper.eq(Article::getViewCounts,viewCounts) 指定条件为浏览量值等于 viewCounts。
     * 最后，调用 articleMapper.update(articleUpdate,updateWrapper) 方法来执行更新操作。该方法将根据指定的条件更新文章表中满足条件的文章的浏览量为 viewCounts+1。类似于 SQL 语句：update article set view_count=100 where view_count=99 and id=11。
     */
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts+1);
        LambdaUpdateWrapper<Article> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        //设置一个为了在多线程的环境下线程安全
        updateWrapper.eq(Article::getViewCounts,viewCounts);
        //update article set view_count=100 where view_count=99 and id=11
        articleMapper.update(articleUpdate,updateWrapper);
    }
}
