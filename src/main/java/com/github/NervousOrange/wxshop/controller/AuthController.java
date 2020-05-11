package com.github.NervousOrange.wxshop.controller;

import com.github.NervousOrange.wxshop.service.AuthService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/register")
    public void auth(@RequestBody String tel) {
        authService.sendVerificationCode(tel);
    }

    @PostMapping("/api/login")
    public void login(@RequestBody TelAndCode telAndCode) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                telAndCode.getTel(), telAndCode.getCode());
        usernamePasswordToken.setRememberMe(true);
        SecurityUtils.getSubject().login(usernamePasswordToken);
    }

    static class TelAndCode{
        private String tel;
        private String code;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}


