package com.github.NervousOrange.wxshop;

import com.github.NervousOrange.wxshop.service.TelVerificationServiceTest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github.NervousOrange.wxshop.TestConstant.HTTP_GET;
import static com.github.NervousOrange.wxshop.TestConstant.HTTP_POST;

@ExtendWith(SpringExtension.class)  // Spring 为 JUnit 5 提供的插件，可以在测试中使用 Spring 相关的功能，依赖注入等
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // 集成测试顺序注解
@TestInstance(TestInstance.Lifecycle.PER_CLASS)   // @BeforeAll 需要是 PER_CLASS
// Shared test instance state between test methods as well as between non-static @BeforeAll and @AfterAll methods in the test class.
public class AuthIntegrationTest extends AbstractIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(AuthIntegrationTest.class);

    @BeforeAll
    public void initDatabase() {
        initDataBase();
    }

    @Test
    @Order(1)
    public void getStatusWhenUserIsNotLogged() throws IOException {
        logger.info("---登录与鉴权：获取登录状态 未登录返回 401 Unauthorized---");
        String responseString = initializeHTTPRequest(
                HTTP_GET, "/api/v1/status",
                "", null, HttpStatus.UNAUTHORIZED.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        String message = (String) map.get("message");
        Assertions.assertEquals("Unauthorized", message);
    }

    @Test
    @Order(2)
    public void getCodeWithIncorrectParameter() throws IOException {
        logger.info("---登录与鉴权：请求手机验证码 输入无效手机号时返回 400 Bad Request---");
        String responseString = initializeHTTPRequest(
                HTTP_POST, "/api/v1/code",
                "", TelVerificationServiceTest.INVALID_TEL_PARAMETER,
                HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(3)
    public void getCodeWithCorrectParameter() throws IOException {
        logger.info("---登录与鉴权：请求手机验证码 输入参数正确时返回 200 OK---");
        String responseString = initializeHTTPRequest(
                HTTP_POST, "/api/v1/code",
                "", TelVerificationServiceTest.VALID_TEL_PARAMETER,
                HttpStatus.OK.value());
    }

    @Test
    @Order(4)
    public void failedLogout() throws IOException {
        logger.info("---登录与鉴权：登出 失败返回 401 Unauthorized---");
        String responseString = initializeHTTPRequest(
                HTTP_GET, "/api/v1/logout",
                "", null, HttpStatus.UNAUTHORIZED.value());
        Map<String, String> map = objectMapper.readValue(responseString, Map.class);
        String message = (String) map.get("message");
        Assertions.assertEquals("Unauthorized", message);
    }

    @Test
    @Order(5)
    public void loginWithIncorrectParameter() throws IOException {
        logger.info("---登录与鉴权：登录 输入无效手机号时返回 400 Bad Request---");
        String responseString = initializeHTTPRequest(
                HTTP_POST, "/api/v1/login",
                "", TelVerificationServiceTest.INVALID_TEL_PARAMETER,
                HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(6)
    public void loginWithWrongPassword() throws IOException {
        logger.info("---登录与鉴权：登录 错误的密码 返回 403 Forbidden---");
        String responseString = initializeHTTPRequest(
                HTTP_POST, "/api/v1/login",
                "", TelVerificationServiceTest.WRONG_LOGIN_PARAMETER, HttpStatus.FORBIDDEN.value());
    }

    @Test
    @Order(7)
    public void successfulLogin() throws IOException {
        logger.info("---登录与鉴权：登录 成功返回 200 OK---");
        String responseString = initializeHTTPRequest(
                HTTP_POST, "/api/v1/login",
                "", TelVerificationServiceTest.VALID_TEL_PARAMETER, HttpStatus.OK.value());
    }

    @Test
    @Order(8)
    public void getStatusWhenUserIsLogged() throws IOException {
        logger.info("---登录与鉴权：获取登录状态 已登录返回 200 OK---");
        String responseString = initializeHTTPRequest(
                HTTP_GET, "/api/v1/status",
                "", null, HttpStatus.OK.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        Assertions.assertTrue((Boolean) map.get("login"));
        Map userMap = (Map) map.get("user");
        Assertions.assertEquals(TelVerificationServiceTest.VALID_TEL_PARAMETER.getTel(), userMap.get("tel"));
    }

    @Test
    @Order(9)
    public void successfulLogout() throws IOException {
        logger.info("---登录与鉴权：登出 成功返回 200 OK---");
        String responseString = initializeHTTPRequest(
                HTTP_GET, "/api/v1/logout",
                "", null, HttpStatus.OK.value());
    }

    @Test
    @Order(10)
    public void successfulLoginByCookie() throws IOException {
        successfulLoginReturnSetCookie();
        getStatusWhenUserIsLogged();
        successfulLogout();
        getStatusWhenUserIsNotLogged();
        successfulLoginWithCookie();
        getStatusWhenUserIsLogged();
        successfulLogout();   // 要登出，这样 testLoginFilter 才能通过测试 返回 401
    }

    @Test  // 测试过滤器前需要退出登录
    @Order(11)
    public void testLoginFilter() throws IOException {
        logger.info("---登录与鉴权：匿名拦截器 未登录返回 401 Unauthorized---");
        initializeHTTPRequest(HTTP_GET, "/api/v1/any", "", null, HttpStatus.UNAUTHORIZED.value());
    }

    public void successfulLoginReturnSetCookie() throws IOException {
        logger.info("---登录与鉴权：获取 cookie---");
        HttpPost httpPost = new HttpPost(getUrl("/api/v1/login"));
        httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(TelVerificationServiceTest.VALID_TEL_PARAMETER)));
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            logger.info(String.valueOf(response.getStatusLine()));
            List<Cookie> cookies = cookieStore.getCookies();
            // logger.info(objectMapper.writeValueAsString(cookies));
            COOKIE = cookies.get(0).getValue();
        } finally {
            Objects.requireNonNull(response).close();
        }
    }

    private void successfulLoginWithCookie() throws IOException {
        logger.info("---登录与鉴权：用 Cookie 登录 成功返回 200 OK---");
        HttpPost httpPost = new HttpPost(getUrl("/api/v1/login"));
        httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpPost.setHeader("Cookie", COOKIE);
        httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(TelVerificationServiceTest.VALID_TEL_PARAMETER)));
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            logger.info(String.valueOf(response.getStatusLine()));
            Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusLine().getStatusCode());
        }
    }
}
