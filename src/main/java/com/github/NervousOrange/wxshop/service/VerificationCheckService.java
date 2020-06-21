package com.github.NervousOrange.wxshop.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class VerificationCheckService {
    private final ConcurrentHashMap<String, String> telToCorrectCode = new ConcurrentHashMap<>();

    public void saveCorrectCode(String tel, String correctCode) {
        telToCorrectCode.put(tel, correctCode);
    }

    public String getCredentials(String tel) {
        return telToCorrectCode.get(tel);
    }
}
