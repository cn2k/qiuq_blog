package com.kabu.blog.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kabu.blog.Service.VisitersService;
import com.kabu.blog.dao.mapper.VisitersMapper;
import com.kabu.blog.dao.pojo.Visiters;
import com.kabu.blog.vo.ErrorCode;
import com.kabu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitersServiceimpl implements VisitersService {
    @Autowired
    private VisitersMapper visitersMapper;

    @Override
    public Result add(Visiters visiters) {
        System.out.println(visiters);
        return Result.success(visitersMapper.insert(visiters));
    }

    @Override
    public Result select() {
        List<Visiters> visiters = visitersMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(visiters);
    }
}
