package com.kabu.blog.utils;

import java.util.Random;

public class RandomAvatar {
    public static int  randomnumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(6) + 1; // 生成一个1到6之间的随机数
        return randomNumber;
    }

    public static void main(String[] args) {
        String pic="../static/user/"+randomnumber()+".jpg";
        System.out.println(pic);
    }
}
