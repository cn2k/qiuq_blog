package com.kabu.blog.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kabu.blog.Service.LoginService;
import com.kabu.blog.Service.SysUserService;
import com.kabu.blog.dao.mapper.SysUserMapper;
import com.kabu.blog.dao.pojo.Article;
import com.kabu.blog.dao.pojo.SysUser;
import com.kabu.blog.vo.ErrorCode;
import com.kabu.blog.vo.LoginUserVo;
import com.kabu.blog.vo.Result;
import com.kabu.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private LoginService loginService;
    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
        if(sysUser==null){
            sysUser=new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("kabuqinuo");
        }
        UserVo userVo=new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        return userVo;
    }

    @Override
    public SysUser findUserById(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
        if(sysUser==null){
            sysUser=new SysUser();
            sysUser.setNickname("kabuqinuo");
        }
        return sysUser;
    }

    @Override
    public  SysUser  findUser(String account, String password) {
        //查询条件,根据用户名和加密后的密码来进行判断
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getId,SysUser::getAccount,SysUser::getAvatar,SysUser::getNickname);
        //提高查询效率
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserBytoken(String token) {
        /**
         * 1.token合法性校验
             * 是否为空,解析是否成功，redis是否存在
         * 2.校验失败,返回信息
         * 3.如果解析成功返回vo对象,LoginVo
         */
        //校验token
        SysUser sysUser = loginService.CheckToken(token);
        if(sysUser==null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        return this.sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        //新增用户
        this.sysUserMapper.insert(sysUser);
    }
}
