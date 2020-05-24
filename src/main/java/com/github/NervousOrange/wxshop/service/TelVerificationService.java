package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.controller.AuthController;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * 对接收到的手机号参数进行有效性验证
 */

@Service
public class TelVerificationService {

    private static Pattern telPattern = Pattern.compile("1[0-9]{10}");

    public boolean isTelParameterValid(AuthController.TelAndCode telAndCode) {
        if (telAndCode == null) {
            return false;
        } else if (telAndCode.getTel() == null) {
            return false;
        } else {
            return telPattern.matcher(telAndCode.getTel()).matches();
        }
    }
}
