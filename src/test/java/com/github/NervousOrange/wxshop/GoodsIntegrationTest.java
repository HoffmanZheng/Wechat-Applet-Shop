package com.github.NervousOrange.wxshop;

import com.github.NervousOrange.wxshop.generated.Goods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.HashMap;
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
                "", TEST_SHOP, HttpStatus.OK.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        Map<String, Object> shopMap = (HashMap<String, Object>) map.get("data");
        Assertions.assertNotNull(shopMap);
        return (int) shopMap.get("id");
    }

    @Test
    public void successfulCreateGoods() throws IOException {
        loginBeforeFunctionalTest();
        shopId = createShopBeforeGoodsTest();
        TEST_GOODS.setShopId(shopId);
        String responseString = initializeHTTPRequest(
                HTTP_POST, "/api/v1/goods",
                "", TEST_GOODS, HttpStatus.CREATED.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        Map<String, String> goodsMap = (Map<String, String>) map.get("data");
        Assertions.assertNotNull(goodsMap.get("id"));
        Assertions.assertNotNull(goodsMap.get("createdAt"));
        Assertions.assertNotNull(goodsMap.get("updatedAt"));
        Assertions.assertEquals(TEST_GOODS.getName(), goodsMap.get("name"));
        Assertions.assertEquals(TEST_GOODS.getDescription(), goodsMap.get("description"));
        Assertions.assertEquals(TEST_GOODS.getDetails(), goodsMap.get("details"));
        Assertions.assertEquals(TEST_GOODS.getImgUrl(), goodsMap.get("imgUrl"));
        Assertions.assertEquals(TEST_GOODS.getPrice(), Long.parseLong(goodsMap.get("price")));
        Assertions.assertEquals(TEST_GOODS.getStock(), Integer.parseInt(goodsMap.get("stock")));
        Assertions.assertEquals(TEST_GOODS.getShopId(), Integer.parseInt(goodsMap.get("shopId")));
        logoutAfterFunctionalTest();
    }

    @Test
    public void forbiddenCreateGoods() throws IOException {
        anotherUserLoginBeforeFunctionalTest();
        Integer anotherShopId = createShopBeforeGoodsTest();  // 尝试在别人的店铺中创建商品
        TEST_GOODS.setShopId(anotherShopId);
        String responseString = initializeHTTPRequest(
                HTTP_POST, "/api/v1/goods",
                "", TEST_GOODS, HttpStatus.FORBIDDEN.value());
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
                "", TEST_GOODS, HttpStatus.UNAUTHORIZED.value());
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
