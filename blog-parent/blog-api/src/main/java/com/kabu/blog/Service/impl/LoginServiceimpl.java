package com.kabu.blog.Service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kabu.blog.Service.LoginService;
import com.kabu.blog.Service.SysUserService;
import com.kabu.blog.dao.pojo.SysUser;
import com.kabu.blog.utils.JWTUtils;
import com.kabu.blog.utils.UserThreadLocal;
import com.kabu.blog.vo.ErrorCode;
import org.apache.commons.codec.digest.DigestUtils;
import com.kabu.blog.vo.Result;
import com.kabu.blog.vo.params.LoginParams;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.kabu.blog.utils.DateUtils.getDateToString;
import static com.kabu.blog.utils.RandomAvatar.randomnumber;

@Service
@Transactional
public class LoginServiceimpl implements LoginService {
    private static final String slat = "kabu!@#";
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1.检查输入参数是否合法
         * 2.根据用户名和密码检查数据是否存在
         * 3.如果不存在则提示登陆失败
         * 4.如果存在则使用jwt,生成token返回前端
         * 5.token放入rides中，双重认证
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        //将密码加密
        String pwd=DigestUtils.md5Hex(password+slat);
        //如果不存在则提示登陆失败
        SysUser sysUser=sysUserService.findUser(account,pwd);
        if(sysUser==null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //存在生成token
        String token = JWTUtils.createToken(sysUser.getId());
        //将token携带用户对象存储到redis中，设置过期时间
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser CheckToken(String token) {
        //如果为空
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap==null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        /**
         * 只需要删除redis里面的token便可以成功登出
         */
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams registParams) {
        String account = registParams.getAccount();
        String password = registParams.getPassword();
        String nickname = registParams.getNickname();
        //判断参数是否为空
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        //判断账户是否存在，存在提示已经存在
        SysUser sysUser = this.sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        //注册新账户
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("../static/user/"+randomnumber()+".jpg");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);

        //token
        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("admin"+slat));
    }
}
