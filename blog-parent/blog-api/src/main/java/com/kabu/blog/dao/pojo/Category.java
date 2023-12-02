package com.kabu.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class Category {
    //文章分类
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}