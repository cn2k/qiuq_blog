package com.kabu.blog.vo.params;

import lombok.Data;

@Data
public class LoginParams {
    private String Account;
    private String Password;
    private String nickname;
}