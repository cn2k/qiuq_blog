package com.kabu.blog.utils;

import com.kabu.blog.dao.pojo.SysUser;

public class UserThreadLocal {
    //本地线程组,将获取的对象保存到本地

    private UserThreadLocal(){}

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }
    public static SysUser get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
