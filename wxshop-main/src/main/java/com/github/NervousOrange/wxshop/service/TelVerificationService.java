package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.controller.AuthController;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * 对接收到的手机号参数进行有效性验证
 */

@Service
public class TelVerificationService {

    // 静态属性，提前编译正则，免去每次使用时的编译工作，提高性能
    private static final Pattern TEL_PATTERN = Pattern.compile("1[0-9]{10}");

    /**
     * 验证输入的手机号参数是否合法
     *
     * @param telAndCode 用户输入的参数
     * @return true 合法 false 不合法
     */
    public boolean isTelParameterValid(AuthController.TelAndCode telAndCode) {
        if (telAndCode == null) {
            return false;
        } else if (telAndCode.getTel() == null) {
            return false;
        } else {
            return TEL_PATTERN.matcher(telAndCode.getTel()).matches();
        }
    }
}
