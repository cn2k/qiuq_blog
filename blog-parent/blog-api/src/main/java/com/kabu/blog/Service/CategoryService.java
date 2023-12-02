package com.kabu.blog.Service;

import com.kabu.blog.vo.CategoryVo;
import com.kabu.blog.vo.Result;

import java.util.List;

public interface CategoryService {
    /**
     * 查找文章分类
     * @param categoryId
     * @return
     */
    CategoryVo findcategoryByid(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoriesDetailById(Long id);
}
