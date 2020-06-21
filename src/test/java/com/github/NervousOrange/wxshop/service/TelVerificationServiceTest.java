package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.controller.AuthController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TelVerificationServiceTest {

    public static AuthController.TelAndCode VALID_TEL_PARAMETER = new AuthController.TelAndCode("13812345678", "000000");
    public static AuthController.TelAndCode INVALID_TEL_PARAMETER = new AuthController.TelAndCode("1381235678", null);
    private static AuthController.TelAndCode EMPTY_TEL_PARAMETER = new AuthController.TelAndCode(null, null);
    private static AuthController.TelAndCode NULL_TEL_PARAMETER = null;

    @Test
    void isTelParameterValid() {
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
