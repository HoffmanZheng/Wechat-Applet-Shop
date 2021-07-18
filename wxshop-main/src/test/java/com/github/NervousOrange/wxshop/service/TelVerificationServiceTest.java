package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.controller.AuthController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelVerificationServiceTest {

    public static AuthController.TelAndCode VALID_TEL_PARAMETER = new AuthController.TelAndCode("13812345678", "000000");
    public static AuthController.TelAndCode ANOTHER_VALID_TEL_PARAMETER = new AuthController.TelAndCode("13812345679", "000000");
    public static AuthController.TelAndCode WRONG_LOGIN_PARAMETER = new AuthController.TelAndCode("13812345678", "000001");
    public static AuthController.TelAndCode INVALID_TEL_PARAMETER = new AuthController.TelAndCode("1381235678", null);
    private static final AuthController.TelAndCode EMPTY_TEL_PARAMETER = new AuthController.TelAndCode(null, null);
    private static final AuthController.TelAndCode NULL_TEL_PARAMETER = null;
    private static final Logger logger = LoggerFactory.getLogger(TelVerificationServiceTest.class);

    @Test
    void isTelParameterValid() {
        logger.info("---用户注册获取验证码，参数校验测试---");
        Assertions.assertTrue(
                new TelVerificationService().isTelParameterValid(VALID_TEL_PARAMETER));
        Assertions.assertFalse(
                new TelVerificationService().isTelParameterValid(INVALID_TEL_PARAMETER));
        Assertions.assertFalse(
                new TelVerificationService().isTelParameterValid(EMPTY_TEL_PARAMETER));
        Assertions.assertFalse(
                new TelVerificationService().isTelParameterValid(NULL_TEL_PARAMETER));
    }
}
