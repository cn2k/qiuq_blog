package com.kabu.blog.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kabu.blog.Service.LoginService;
import com.kabu.blog.dao.pojo.SysUser;
import com.kabu.blog.utils.UserThreadLocal;
import com.kabu.blog.vo.ErrorCode;
import com.kabu.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
@Slf4j
public class LoginInteceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;
    //实现拦截器

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //执行controller进行的操作
        /**
         * 1. 需要判断接口请求是否正确
         * 2. 判断token是否为null 为空则未登录
         * 3. 如果不为空则验证token
         * 4. 认证成功 放行
         */
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //判断是否为空,为空则提示未登录
        String token = request.getHeader("oauth-token");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("{}: {}", headerName, request.getHeader(headerName));
        }
        log.info("=================request end===========================");


        if(StringUtils.isBlank(token)){
            Result res = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(res));
            return false;
        }
        //判断当前token是否正确
        SysUser sysUser = loginService.CheckToken(token);
        if(sysUser==null){
            Result res = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(res));
            return false;
        }
        //登陆验证成功 放行
        //将对象放入controller,直接获取用户信息
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public final void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //每次用完即清除UserThreadLocal,防止内存溢出
        UserThreadLocal.remove();
    }
}
