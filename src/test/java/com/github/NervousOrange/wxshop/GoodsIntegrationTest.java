package com.github.NervousOrange.wxshop;

import com.github.NervousOrange.wxshop.generated.Goods;
import com.github.NervousOrange.wxshop.generated.Shop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Map;

import static com.github.NervousOrange.wxshop.ShopIntegrationTest.TEST_SHOP;
import static com.github.NervousOrange.wxshop.TestConstant.HTTP_POST;
import static com.github.NervousOrange.wxshop.common.constant.StringConstants.SHOP_NOT_AUTHORIZED;

@ExtendWith(SpringExtension.class)  // Spring 为 JUnit 5 提供的插件，可以在测试中使用 Spring 相关的功能，依赖注入等
@TestInstance(TestInstance.Lifecycle.PER_CLASS)   // @BeforeAll 需要是 PER_CLASS
// Shared test instance state between test methods as well as between non-static @BeforeAll and @AfterAll methods in the test class.
public class GoodsIntegrationTest extends AbstractIntegrationTest {

    private static Integer shopId = null;
    private static Goods TEST_GOODS = new Goods();

    static {
        TEST_GOODS.setName("肥皂");
        TEST_GOODS.setDescription("纯天然无污染肥皂");
        TEST_GOODS.setDetails("这是一块好肥皂");
        TEST_GOODS.setImgUrl("https://img.url");
        TEST_GOODS.setPrice(500L);
        TEST_GOODS.setStock(10);
    }

    @BeforeAll
    public void initDatabase() {
        initDataBase();
    }

    private int createShopBeforeGoodsTest() throws IOException {
        String responseString = initializeHTTPRequest(
                HTTP_POST, "/api/v1/shop",
                null, TEST_SHOP, HttpStatus.OK.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        Shop shop = (Shop) map.get("data");
        Assertions.assertNotNull(shop);
        return shop.getId();
    }

    @Test
    public void successfulCreateGoods() throws IOException {
        loginBeforeFunctionalTest();
        shopId = createShopBeforeGoodsTest();
        TEST_GOODS.setShopId(shopId);
        String responseString = initializeHTTPRequest(
                HTTP_POST, "/api/v1/goods",
                null, TEST_GOODS, HttpStatus.CREATED.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        Goods goods = (Goods) map.get("data");
        Assertions.assertNotNull(goods.getId());
        Assertions.assertNotNull(goods.getCreatedAt());
        Assertions.assertNotNull(goods.getUpdatedAt());
        Assertions.assertEquals(TEST_GOODS.getName(), goods.getName());
        Assertions.assertEquals(TEST_GOODS.getDescription(), goods.getDescription());
        Assertions.assertEquals(TEST_GOODS.getDetails(), goods.getDetails());
        Assertions.assertEquals(TEST_GOODS.getImgUrl(), goods.getImgUrl());
        Assertions.assertEquals(TEST_GOODS.getPrice(), goods.getPrice());
        Assertions.assertEquals(TEST_GOODS.getStock(), goods.getStock());
        Assertions.assertEquals(TEST_GOODS.getShopId(), goods.getShopId());
        logoutAfterFunctionalTest();
    }

    @Test
    public void forbiddenCreateGoods() throws IOException {
        anotherUserLoginBeforeFunctionalTest();
        Integer anotherShopId = createShopBeforeGoodsTest();  // 尝试在别人的店铺中创建商品
        TEST_GOODS.setShopId(anotherShopId);
        String responseString = initializeHTTPRequest(
                HTTP_POST, "/api/v1/goods",
                null, TEST_GOODS, HttpStatus.FORBIDDEN.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        String message = (String) map.get("message");
        Assertions.assertEquals(SHOP_NOT_AUTHORIZED, message);
        logoutAfterFunctionalTest();
    }

    @Test
    public void unauthorizedCreateGoods() throws IOException {
        // 没有登录去创建商品
        TEST_GOODS.setShopId(shopId);
        String responseString = initializeHTTPRequest(HTTP_POST, "/api/v1/goods",
                null, TEST_GOODS, HttpStatus.UNAUTHORIZED.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        String message = (String) map.get("message");
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.getReasonPhrase(), message);
    }


    @Test
    public void testGetGoods() {

    }

    @Test
    public void testUpdateGoods() {

    }

    @Test
    public void testDeleteGoods() {

    }

}
