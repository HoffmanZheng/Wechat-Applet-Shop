package com.github.NervousOrange.wxshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final SmsCodeService smsCodeService;  // 注入的是接口，方便后期替换
    private final VerificationCheckService verificationCheckService;


    @Autowired
    public AuthService(UserService userService, SmsCodeService smsCodeService, VerificationCheckService verificationCheckService) {
        this.userService = userService;
        this.smsCodeService = smsCodeService;
        this.verificationCheckService = verificationCheckService;
    }

    public void sendVerificationCode(String tel) {
        // 查看数据库中该用户是否存在，如果不存在就创建
        userService.createUserIfNotExist(tel);
        String correctCode = smsCodeService.sendSmsCode(tel);
        verificationCheckService.saveCorrectCode(tel, correctCode);
    }
}
