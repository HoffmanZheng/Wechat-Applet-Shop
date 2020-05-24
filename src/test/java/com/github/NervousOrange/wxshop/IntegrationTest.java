package com.github.NervousOrange.wxshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.NervousOrange.wxshop.service.TelVerificationServiceTest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WxshopApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.yml")
public class IntegrationTest {
    @Autowired
    Environment environment;

    private ObjectMapper objectMapper = new ObjectMapper();
    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    @Test
    public void returnHttPOKWhenTelParameterIsCorrect() throws IOException {
        HttpPost httpPost = new HttpPost(getUrl("/api/register"));
        httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(TelVerificationServiceTest.VALID_TEL_PARAMETER)));

        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            System.out.println(response.getStatusLine());
            Assertions.assertTrue((response.getStatusLine().getStatusCode()) < 400);
        }
    }

    @Test
    public void returnBadRequestWhenInvalidInputTelParameter() throws IOException {
        HttpPost httpPost = new HttpPost(getUrl("/api/register"));
        httpPost.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(TelVerificationServiceTest.INVALID_TEL_PARAMETER)));

        try (CloseableHttpResponse response2 = httpclient.execute(httpPost)) {
            System.out.println(response2.getStatusLine());
            Assertions.assertTrue((response2.getStatusLine().getStatusCode()) >= 400);
        }
    }

    public String getUrl(String apiName) {
        return "http://localhost:" + environment.getProperty("local.server.port") + apiName;
    }
}
