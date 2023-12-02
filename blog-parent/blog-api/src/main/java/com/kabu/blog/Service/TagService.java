package com.kabu.blog.Service;

import com.kabu.blog.vo.Result;
import com.kabu.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);
    //查询所有文章标签
    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
