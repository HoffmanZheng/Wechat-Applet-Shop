package com.github.NervousOrange.wxshop.service;

public interface SmsCodeService {
    /**
     * 手机验证码服务
     * @param tel 输入为手机号
     * @return 返回正确的code
     */
    String sendSmsCode(String tel);


}
