package com.github.NervousOrange.wxshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final MockSmsCodeService mockSmsCodeService;
    private final VerificationCheckService verificationCheckService;


    @Autowired
    public AuthService(UserService userService, MockSmsCodeService mockSmsCodeService, VerificationCheckService verificationCheckService) {
        this.userService = userService;
        this.mockSmsCodeService = mockSmsCodeService;
        this.verificationCheckService = verificationCheckService;
    }

    public void sendVerificationCode(String tel) {
        userService.createUserIfNotExist(tel);
        String correctCode = mockSmsCodeService.sendSmsCode(tel);
        verificationCheckService.saveCorrectCode(tel, correctCode);
    }
}
