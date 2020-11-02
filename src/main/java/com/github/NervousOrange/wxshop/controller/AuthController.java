package com.github.NervousOrange.wxshop.controller;

import com.github.NervousOrange.wxshop.config.UserContext;
import com.github.NervousOrange.wxshop.entity.LoginResponse;
import com.github.NervousOrange.wxshop.generated.User;
import com.github.NervousOrange.wxshop.service.AuthService;
import com.github.NervousOrange.wxshop.service.TelVerificationService;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
@Api(value = "AuthController", tags = "登录与鉴权")
public class AuthController {
    private final AuthService authService;
    private final TelVerificationService telVerificationService;

    @Autowired
    public AuthController(AuthService authService, TelVerificationService telVerificationService) {
        this.authService = authService;
        this.telVerificationService = telVerificationService;
    }

    @PostMapping("/code")
    @ApiOperation(value = "根据手机号获取验证码")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK，成功发送验证码"),
            @ApiResponse(code = 400, message = "Bad Request，用户的请求包含错误")})
    public void code(@ApiParam(value = "用户手机号", example = "13812341234")
                     @RequestBody TelAndCode telAndCode, HttpServletResponse response) {
        if (telVerificationService.isTelParameterValid(telAndCode)) {
            authService.sendVerificationCode(telAndCode.getTel());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "使用手机号+验证码登录")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK，登录成功"),
            @ApiResponse(code = 400, message = "Bad Request，用户的请求包含错误"),
            @ApiResponse(code = 403, message = "Forbidden，用户的验证码错误")})
    public void login(@ApiParam(value = "用户手机号 + 验证码")
                      @RequestBody TelAndCode telAndCode,
                      HttpServletResponse response) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                telAndCode.getTel(), telAndCode.getCode());
        usernamePasswordToken.setRememberMe(true);
        try {
            SecurityUtils.getSubject().login(usernamePasswordToken);
        } catch (IncorrectCredentialsException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }

    }

    @GetMapping("/status")
    @ApiOperation(value = "获取登录状态")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK，成功获取用户登录状态", response = LoginResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized，用户尚未登录")})
    public LoginResponse status(HttpServletResponse response) {
        User currentUser = UserContext.getCurrentUser();
        if (currentUser != null) {
            return LoginResponse.loggedResponse(currentUser);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return LoginResponse.notLoggedResponse("Unauthorized");
        }
    }

    @GetMapping("/logout")
    @ApiOperation(value = "登出")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK，成功登出", response = LoginResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized，用户尚未登录")})
    public LoginResponse logout(HttpServletResponse response) {
        String tel = (String) SecurityUtils.getSubject().getPrincipal();
        if (tel == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return LoginResponse.notLoggedResponse("Unauthorized");
        } else {
            SecurityUtils.getSubject().logout();
            return LoginResponse.emptyResponse();
        }
    }

    public static class TelAndCode {
        @ApiModelProperty(example = "13812341234", required = true)
        private String tel;

        @ApiModelProperty(example = "000000")
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


