package com.kabu.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kabu.blog.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询文章标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    List<Long> findHostTagIds(int limits);

    List<Tag> findTagsByTagIds(List<Long> tagids);
}
