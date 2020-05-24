package com.github.NervousOrange.wxshop.controller;

import com.github.NervousOrange.wxshop.service.AuthService;
import com.github.NervousOrange.wxshop.service.TelVerificationService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {
    private final AuthService authService;
    private final TelVerificationService telVerificationService;

    @Autowired
    public AuthController(AuthService authService, TelVerificationService telVerificationService) {
        this.authService = authService;
        this.telVerificationService = telVerificationService;
    }

    @PostMapping("/api/register")
    public void auth(@RequestBody TelAndCode telAndCode, HttpServletResponse httpServletResponse) {
        if (telVerificationService.isTelParameterValid(telAndCode)) {
            authService.sendVerificationCode(telAndCode.getTel());
        } else {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    @PostMapping("/api/login")
    public void login(@RequestBody TelAndCode telAndCode) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                telAndCode.getTel(), telAndCode.getCode());
        usernamePasswordToken.setRememberMe(true);
        SecurityUtils.getSubject().login(usernamePasswordToken);
    }

    public static class TelAndCode{
        private String tel;
        private String code;

        public TelAndCode(String tel, String code) {
            this.tel = tel;
            this.code = code;
        }

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


