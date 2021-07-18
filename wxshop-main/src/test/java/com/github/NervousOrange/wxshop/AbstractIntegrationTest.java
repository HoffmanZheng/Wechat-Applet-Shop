package com.github.NervousOrange.wxshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.NervousOrange.wxshop.service.TelVerificationServiceTest;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static com.github.NervousOrange.wxshop.TestConstant.*;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)  // Spring 为 JUnit 5 提供的插件，可以在测试中使用 Spring 相关的功能，依赖注入等
@SpringBootTest(classes = WxshopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.config.location=classpath:test-application.yml"}) // 使用测试数据库，不污染生产库
public class AbstractIntegrationTest {

    @Autowired
    Environment environment;

    protected static final ObjectMapper objectMapper = new ObjectMapper();
    protected static final BasicCookieStore cookieStore = new BasicCookieStore();
    protected static final CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
    protected static String COOKIE;
    private static final Logger logger = LoggerFactory.getLogger(AbstractIntegrationTest.class);

    @Value("${spring.datasource.url}")
    private String testUrl;

    @Value("${spring.datasource.username}")
    private String testUserName;

    @Value("${spring.datasource.password}")
    private String testPassword;

    protected void initDataBase() {
        logger.info("------------init Database------------");
        Flyway flyway = Flyway.configure().dataSource(testUrl, testUserName, testPassword).load();
        flyway.clean();
        flyway.migrate();
    }

    protected void loginBeforeFunctionalTest() throws IOException {
        initializeHTTPRequest(HTTP_POST, "/api/v1/code", "",
                TelVerificationServiceTest.VALID_TEL_PARAMETER, HttpStatus.OK.value());
        initializeHTTPRequest(HTTP_POST, "/api/v1/login", "",
                TelVerificationServiceTest.VALID_TEL_PARAMETER, HttpStatus.OK.value());
    }

    protected void anotherUserLoginBeforeFunctionalTest() throws IOException {
        initializeHTTPRequest(HTTP_POST, "/api/v1/code", "",
                TelVerificationServiceTest.ANOTHER_VALID_TEL_PARAMETER, HttpStatus.OK.value());
        initializeHTTPRequest(HTTP_POST, "/api/v1/login", "",
                TelVerificationServiceTest.ANOTHER_VALID_TEL_PARAMETER, HttpStatus.OK.value());
    }


    protected void logoutAfterFunctionalTest() throws IOException {
        initializeHTTPRequest(HTTP_GET, "/api/v1/logout",
                "", null, HttpStatus.OK.value());
    }

    protected String getUrl(String apiName) {
        String url = "http://localhost:" + environment.getProperty("local.server.port");
        return StringUtils.isEmpty(apiName) ? url : url + apiName;
    }

    protected String initializeHTTPRequest(String httpMethod, String urlInterface,
                                           String requestParam, Object requestBody, int expectedHttpStatus) throws IOException {
        HttpUriRequest httpRequest = null;
        switch (httpMethod) {
            case HTTP_GET:
                HttpGet httpGet = new HttpGet(getUrl(urlInterface) + requestParam);
                httpRequest = httpGet;
                break;
            case HTTP_POST:
                HttpPost httpPost = new HttpPost(getUrl(urlInterface) + requestParam);
                httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                StringEntity stringEntity = new StringEntity(objectMapper.writeValueAsString(requestBody), APPLICATION_JSON);
                stringEntity.setChunked(true);
                httpPost.setEntity(stringEntity);
                httpRequest = httpPost;
                break;
            case HTTP_PATCH:
                HttpPatch httpPatch = new HttpPatch(getUrl(urlInterface) + requestParam);
                httpPatch.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                httpPatch.setEntity(new StringEntity(objectMapper.writeValueAsString(requestBody), APPLICATION_JSON));
                httpRequest = httpPatch;
                break;
            case HTTP_DELETE:
                HttpDelete httpDelete = new HttpDelete(getUrl(urlInterface) + requestParam);
                httpRequest = httpDelete;
                break;
        }
        assert httpRequest != null;
        httpRequest.setHeader("Expect", "100-continue");
        try (CloseableHttpResponse response = httpclient.execute(httpRequest)) {
            logger.info(String.valueOf(response.getStatusLine()));
            Assertions.assertEquals(expectedHttpStatus, response.getStatusLine().getStatusCode());
            String responseString = EntityUtils.toString(response.getEntity());
            logger.info(responseString);
            return responseString;
        }

        /**
         * HTTP/1.1 302  [Set-Cookie: JSESSIONID=f8fe626f-984b-4a84-9e79-8db7ee88df5e; Path=/; HttpOnly; SameSite=lax,
         * Location: http://localhost:10435/login.jsp, Content-Length: 0, Date: Tue, 15 Jun 2021 13:58:02 GMT,
         * Keep-Alive: timeout=60, Connection: keep-alive] [Content-Length: 0,Chunked: false]
         */
    }
}
