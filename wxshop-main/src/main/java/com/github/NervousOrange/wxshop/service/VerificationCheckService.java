package com.github.NervousOrange.wxshop.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 将手机号及正确的验证码暂存的服务
 * 提供手机验证码验证
 */
@Service
public class VerificationCheckService {
    private static final ConcurrentHashMap<String, String> telToCorrectCode = new ConcurrentHashMap<>();

    public void saveCorrectCode(String tel, String correctCode) {
        telToCorrectCode.put(tel, correctCode);
    }

    public String getCredentials(String tel) {
        return telToCorrectCode.get(tel);
    }
}
