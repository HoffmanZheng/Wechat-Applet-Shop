package com.github.NervousOrange.wxshop.controller;

import com.github.NervousOrange.wxshop.config.UserContext;
import com.github.NervousOrange.wxshop.entity.LoginResponse;
import com.github.NervousOrange.wxshop.generated.User;
import com.github.NervousOrange.wxshop.service.AuthService;
import com.github.NervousOrange.wxshop.service.TelVerificationService;
import com.github.NervousOrange.wxshop.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;
    private final TelVerificationService telVerificationService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, TelVerificationService telVerificationService, UserService userService) {
        this.authService = authService;
        this.telVerificationService = telVerificationService;
        this.userService = userService;
    }

    /**
     * @api {post} /code 使用手机号，请求验证码
     * @apiName code
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {String} tel 手机号码
     * @apiParamExample {json} Request-Example:
     *          {
     *              "tel": "13812345678"
     *          }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     * @apiError 400 Bad Request 若用户的请求包含错误
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "Bad Request"
     *     }
     */
    @PostMapping("/code")
    public void code(@RequestBody TelAndCode telAndCode, HttpServletResponse response) {
        if (telVerificationService.isTelParameterValid(telAndCode)) {
            // 查看数据库中该用户是否存在，如果不存在就创建
            authService.sendVerificationCode(telAndCode.getTel());
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     * @api {post} /login 登录
     * @apiName login
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {String} tel 手机号码
     * @apiParam {String} code 验证码
     * @apiParamExample {json} Request-Example:
     *          {
     *              "tel": "13812345678",
     *              "code": "000000"
     *          }
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     * @apiError 400 Bad Request 若用户的请求包含错误
     * @apiError 403 Forbidden 若用户的验证码错误
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *       "message": "Bad Request"
     *     }
     */
    @PostMapping("/login")
    public void login(@RequestBody TelAndCode telAndCode, HttpServletResponse response) {
        if (!telVerificationService.isTelParameterValid(telAndCode)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                    telAndCode.getTel(), telAndCode.getCode());
            usernamePasswordToken.setRememberMe(true);  // 存储 cookie，在一段时间内有效，不必重复登录
            try {
                SecurityUtils.getSubject().login(usernamePasswordToken);
            } catch (IncorrectCredentialsException e) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        }
    }

    /**
     * @api {get} /status 获取登录状态
     * @apiName status
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiSuccess {User} user 用户信息
     * @apiSuccess {Boolean} login 登录状态
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "login": true,
     *         "user": {
     *             "id": 123,
     *             "name": "张三",
     *             "tel": "13812345678",
     *             "avatarUrl": "https://url",
     *             "address": "北京市 西城区 100号",
     *         }
     *      }
     * @apiError 401 Unauthorized 用户未登录
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *        "message": "Unauthorized"
     *     }
     */
    @GetMapping("/status")
    public LoginResponse status(HttpServletResponse response) {
        User currentUser = UserContext.getCurrentUser();
        if (currentUser != null) {
            return LoginResponse.loggedResponse(currentUser);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return LoginResponse.notLoggedResponse(UNAUTHORIZED.getReasonPhrase());
        }
    }

    /**
     * @api {get} /logout 登出
     * @apiName logout
     * @apiGroup 登录与鉴权
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *
     * @apiError 401 Unauthorized 用户未登录
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *        "message": "Unauthorized"
     *     }
     */
    @GetMapping("/logout")
    public LoginResponse logout(HttpServletResponse response) {
        String tel = (String) SecurityUtils.getSubject().getPrincipal();
        if (tel == null) {
            response.setStatus(UNAUTHORIZED.value());
            return LoginResponse.notLoggedResponse(UNAUTHORIZED.getReasonPhrase());
        } else {
            SecurityUtils.getSubject().logout();
            return LoginResponse.emptyResponse();
        }
    }

    public static class TelAndCode{
        private String tel;
        private String code;

        public TelAndCode() {
        }

        public TelAndCode(String tel, String code) {
            this.tel = tel;
            this.code = code;
        }

        public String getTel() {
            return tel;
        }

        public String getCode() {
            return code;
        }

    }
}


