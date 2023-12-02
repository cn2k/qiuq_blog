package com.kabu.blog.Service;

import com.kabu.blog.dao.pojo.Visiters;
import com.kabu.blog.vo.Result;

public interface VisitersService {
    Result add(Visiters visiters);

    Result select();

}
