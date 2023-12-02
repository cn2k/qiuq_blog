package com.kabu.blog.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Visiters {
    @TableId(type = IdType.AUTO)
    private int id;

    private String nickname;

    private String email;

    private String content;
}
