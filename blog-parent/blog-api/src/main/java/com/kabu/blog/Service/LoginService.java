package com.kabu.blog.Service;

import com.kabu.blog.dao.pojo.SysUser;
import com.kabu.blog.vo.Result;
import com.kabu.blog.vo.params.LoginParams;

public interface LoginService {
    /**
     * 登录功能
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);

    SysUser CheckToken(String token);

    /**
     * 登出功能
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册功能
     * @param registParams
     * @return
     */
    Result register(LoginParams registParams);
}
