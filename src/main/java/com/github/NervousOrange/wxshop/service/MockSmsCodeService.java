package com.github.NervousOrange.wxshop.service;

import org.springframework.stereotype.Service;

// Mock 模拟接收手机验证码
@Service
public class MockSmsCodeService implements SmsCodeService{

    @Override
    public String sendSmsCode(String tel) {
        return "000000";
    }

    /**
     * TODO：可能存在的风险
     *
     * 1. 有没有流量控制：短信轰炸
     * 2. 验证码被暴力破解
     * 3. 超时的问题
     */
}
