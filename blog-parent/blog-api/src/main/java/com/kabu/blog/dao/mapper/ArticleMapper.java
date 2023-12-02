package com.kabu.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kabu.blog.dao.dos.Archive;
import com.kabu.blog.dao.pojo.Article;
import org.apache.ibatis.annotations.Update;

import java.util.List;

//与article对象进行关联
public interface ArticleMapper extends BaseMapper<Article> {
    List<Archive> listArchives();

    IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}
