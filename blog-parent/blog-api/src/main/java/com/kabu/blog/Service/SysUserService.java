package com.kabu.blog.Service;

import com.kabu.blog.dao.pojo.SysUser;
import com.kabu.blog.vo.Result;
import com.kabu.blog.vo.UserVo;

public interface SysUserService {
    UserVo findUserVoById(Long id);
    //根据article中的id查询作者id
    SysUser findUserById(Long authorId);

    SysUser findUser(String username, String password);
//根据用户token查询用户信息
    Result findUserBytoken(String token);

    /**
     * 是否存在相同账户用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 添加用户
     * @param sysUser
     */
    void save(SysUser sysUser);
}
