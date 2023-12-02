package com.kabu.blog.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
//与数据库进行分离
@Data
public class TagVo {
    private Long id;

    private String tagName;
}
