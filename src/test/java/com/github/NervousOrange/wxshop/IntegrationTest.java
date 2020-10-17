package com.github.NervousOrange.wxshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.NervousOrange.wxshop.service.TelVerificationServiceTest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WxshopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.yml")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {
    @Autowired
    Environment environment;

    private ObjectMapper objectMapper = new ObjectMapper();
    private static BasicCookieStore cookieStore = new BasicCookieStore();
    private static CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
    private static String COOKIE;

    @Test
    @Order(1)
    public void getStatusWhenUserIsNotLogged() throws IOException {
        System.out.println("测试 —— 获取登录状态：未登录返回 401 Unauthorized");
        String responseString = initializeHTTPRequest(
                true, "/api/v1/status",
                "", null, HttpStatus.UNAUTHORIZED.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        String message = (String) map.get("message");
        Assertions.assertEquals("Unauthorized", message);
    }

    @Test
    @Order(2)
    public void getCodeWithIncorrectParameter() throws IOException {
        System.out.println("测试 —— 请求手机验证码：输入无效手机号时返回 400 Bad Request");
        String responseString = initializeHTTPRequest(
                false, "/api/v1/code",
                "", TelVerificationServiceTest.INVALID_TEL_PARAMETER,
                HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Order(3)
    public void getCodeWithCorrectParameter() throws IOException {
        System.out.println("测试 —— 请求手机验证码：输入参数正确时返回 200 OK");
        String responseString = initializeHTTPRequest(
                false, "/api/v1/code",
                "", TelVerificationServiceTest.VALID_TEL_PARAMETER,
                HttpStatus.OK.value());
    }

    @Test
    @Order(4)
    public void failedLogout() throws IOException {
        System.out.println("测试 —— 登出：失败返回 401 Unauthorized");
        String responseString = initializeHTTPRequest(
                true, "/api/v1/logout",
                "", null, HttpStatus.UNAUTHORIZED.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        String message = (String) map.get("message");
        Assertions.assertEquals("Unauthorized", message);
    }

    @Test
    @Order(5)
    public void successfulLogin() throws IOException {
        System.out.println("测试 —— 登录：成功返回 200 OK");
        String responseString = initializeHTTPRequest(
                false, "/api/v1/login",
                "", TelVerificationServiceTest.VALID_TEL_PARAMETER, HttpStatus.OK.value());
    }

    @Test
    @Order(6)
    public void getStatusWhenUserIsLogged() throws IOException {
        System.out.println("测试 —— 获取登录状态：已登录返回 200 OK");
        String responseString = initializeHTTPRequest(
                true, "/api/v1/status",
                "", null, HttpStatus.OK.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        Assertions.assertTrue((Boolean) map.get("login"));
        Map userMap = (Map) map.get("user");
        Assertions.assertEquals(TelVerificationServiceTest.VALID_TEL_PARAMETER.getTel(), userMap.get("tel"));
    }

    @Test
    @Order(7)
    public void successfulLogout() throws IOException {
        System.out.println("测试 —— 登出：成功返回 200 OK");
        String responseString = initializeHTTPRequest(
                true, "/api/v1/logout",
                "", null, HttpStatus.OK.value());
    }

    @Test
    @Order(8)
    public void comprehensiveTest() throws IOException {
        getStatusWhenUserIsNotLogged();
        failedLogout();
        getCodeWithCorrectParameter();  // save info
        getStatusWhenUserIsNotLogged();
        successfulLoginReturnSetCookie();
        getStatusWhenUserIsLogged();
        successfulLogout();
        getStatusWhenUserIsNotLogged();
        successfulLoginWithCookie();
        getStatusWhenUserIsLogged();
    }

    @Test
    public void testLoginFilter() throws IOException {
        System.out.println("测试 —— 匿名拦截器：未登录返回 401 Unauthorized");
        HttpGet httpGet = new HttpGet(getUrl("/api/v1/any"));
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            System.out.println(response.getStatusLine());
            Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusLine().getStatusCode());
        }
    }

    public void successfulLoginReturnSetCookie() throws IOException {
        System.out.println("登录 —— 获取 cookie");
        HttpPost httpPost = new HttpPost(getUrl("/api/v1/login"));
        httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(TelVerificationServiceTest.VALID_TEL_PARAMETER)));
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            System.out.println(response.getStatusLine());
            List<Cookie> cookies = cookieStore.getCookies();
            // System.out.println(objectMapper.writeValueAsString(cookies));
            COOKIE = cookies.get(0).getValue();
        } finally {
            Objects.requireNonNull(response).close();
        }
    }

    private void successfulLoginWithCookie() throws IOException {
        System.out.println("用 Cookie 登录：成功返回 200 OK");
        HttpPost httpPost = new HttpPost(getUrl("/api/v1/login"));
        httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpPost.setHeader("Cookie", COOKIE);
        httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(TelVerificationServiceTest.VALID_TEL_PARAMETER)));
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            System.out.println(response.getStatusLine());
            Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusLine().getStatusCode());
        }
    }

    public String getUrl(String apiName) {
        return "http://localhost:" + environment.getProperty("local.server.port") + apiName;
    }

    private String initializeHTTPRequest(boolean isGet, String urlInterface,
        String requestParam, Object requestBody, int expectedHttpStatus) throws IOException {
        if (isGet) {
            HttpGet httpGet = new HttpGet(getUrl(urlInterface) + requestParam);
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                System.out.println(response.getStatusLine());
                Assertions.assertEquals(expectedHttpStatus, response.getStatusLine().getStatusCode());
                String responseString = EntityUtils.toString(response.getEntity());
                System.out.println(responseString);
                return responseString;
            }
        } else {
            HttpPost httpPost = new HttpPost(getUrl(urlInterface));
            httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(requestBody)));
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                System.out.println(response.getStatusLine());
                Assertions.assertEquals(expectedHttpStatus, response.getStatusLine().getStatusCode());
                String responseString = EntityUtils.toString(response.getEntity());
                System.out.println(responseString);
                return responseString;
            }
        }
    }
}
