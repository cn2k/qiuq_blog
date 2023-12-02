package com.kabu.blog.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kabu.blog.Service.TagService;
import com.kabu.blog.dao.mapper.TagMapper;
import com.kabu.blog.dao.pojo.Tag;
import com.kabu.blog.vo.Result;
import com.kabu.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mybatis无法进行多表查询
        List<Tag> tags=tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }
    //查询所有标签
    @Override
    public Result findAllDetail() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tags = this.tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        TagVo copy = copy(tag);
        return Result.success(copy);
    }


    @Override
    public Result hots(int limit) {
        //每个标签id下面那个文章数量最多,按照这个排序然后升序
        List<Long> tagids=tagMapper.findHostTagIds(limit);
        if(CollectionUtils.isEmpty(tagids)){
            return Result.success(Collections.emptyList());
        }
        //需要的是tag对象
        List<Tag> tagList=tagMapper.findTagsByTagIds(tagids);
        return Result.success(tagList);
    }
    //查询所有标签
    @Override
    public Result findAll() {
        List<Tag> tags = this.tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tags));
    }
}
