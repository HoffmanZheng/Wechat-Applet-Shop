package com.github.NervousOrange.wxshop;

import com.github.NervousOrange.wxshop.generated.Goods;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.NervousOrange.wxshop.ShopIntegrationTest.TEST_SHOP;
import static com.github.NervousOrange.wxshop.TestConstant.*;
import static com.github.NervousOrange.wxshop.common.constant.StringConstants.SHOP_NOT_AUTHORIZED;

@ExtendWith(SpringExtension.class)  // Spring 为 JUnit 5 提供的插件，可以在测试中使用 Spring 相关的功能，依赖注入等
@TestInstance(TestInstance.Lifecycle.PER_CLASS)   // @BeforeAll 需要是 PER_CLASS
// Shared test instance state between test methods as well as between non-static @BeforeAll and @AfterAll methods in the test class.
public class GoodsIntegrationTest extends AbstractIntegrationTest {

    private static Integer SHOP_ID = null;
    private static Goods TEST_GOODS = new Goods();
    private static Integer GOODS_ID = null;
    private static Goods UPDATED_GOODS = new Goods();

    static {
        UPDATED_GOODS.setName("新款肥皂");
        UPDATED_GOODS.setDescription("纳米高科技");
        TEST_GOODS.setName("肥皂");
        TEST_GOODS.setDescription("纯天然无污染肥皂");
        TEST_GOODS.setDetails("这是一块好肥皂");
        TEST_GOODS.setImgUrl("https://img.url");
        TEST_GOODS.setPrice(500L);
        TEST_GOODS.setStock(10);
    }

    @BeforeAll
    public void initDatabase() throws IOException {
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
    @Order(1)
    public void successfulCreateGoods() throws IOException {
        loginBeforeFunctionalTest();
        SHOP_ID = createShopBeforeGoodsTest();
        TEST_GOODS.setShopId(SHOP_ID);
        String responseString = initializeHTTPRequest(
                HTTP_POST, "/api/v1/goods",
                "", TEST_GOODS, HttpStatus.CREATED.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        Map<String, Object> goodsMap = (Map<String, Object>) map.get("data");
        Assertions.assertNotNull(goodsMap.get("id"));
        GOODS_ID = (Integer) goodsMap.get("id");
        Assertions.assertNotNull(goodsMap.get("createdAt"));
        Assertions.assertNotNull(goodsMap.get("updatedAt"));
        Assertions.assertEquals(goodsMap.get("createdAt"), goodsMap.get("updatedAt"));
        Assertions.assertEquals(TEST_GOODS.getName(), goodsMap.get("name"));
        Assertions.assertEquals(TEST_GOODS.getDescription(), goodsMap.get("description"));
        Assertions.assertEquals(TEST_GOODS.getDetails(), goodsMap.get("details"));
        Assertions.assertEquals(TEST_GOODS.getImgUrl(), goodsMap.get("imgUrl"));
        Assertions.assertEquals(TEST_GOODS.getPrice(), ((Integer) goodsMap.get("price")).longValue());
        Assertions.assertEquals(TEST_GOODS.getStock(), (goodsMap.get("stock")));
        Assertions.assertEquals(TEST_GOODS.getShopId(), (goodsMap.get("shopId")));
        logoutAfterFunctionalTest();
    }

    @Test
    @Order(2)
    public void forbiddenCreateGoods() throws IOException {
        anotherUserLoginBeforeFunctionalTest();
        Integer anotherShopId = createShopBeforeGoodsTest();  // 尝试在别人的店铺中创建商品
        logoutAfterFunctionalTest();
        loginBeforeFunctionalTest();
        TEST_GOODS.setShopId(anotherShopId);
        String responseString = initializeHTTPRequest(
                HTTP_POST, "/api/v1/goods",
                "", TEST_GOODS, HttpStatus.FORBIDDEN.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        String message = (String) map.get("message");
        Assertions.assertEquals(SHOP_NOT_AUTHORIZED, message);
    }

    @Test
    public void unauthorizedCreateGoods() throws IOException {
        anotherUserLoginBeforeFunctionalTest();
        logoutAfterFunctionalTest();
        // 没有登录去创建商品
        TEST_GOODS.setShopId(SHOP_ID);
        // TODO: 不知道为啥这个老返回 302 跳转到 Spring Boot 默认的登录页面，而没有被过滤器拦截
        String responseString = initializeHTTPRequest(HTTP_POST, "/api/v1/goods",
                "", TEST_GOODS, HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @Order(3)
    public void testGetGoods() throws IOException {
        String responseString = initializeHTTPRequest(HTTP_GET, "/api/v1/goods/" + GOODS_ID,
                "", null, HttpStatus.OK.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        Map<String, Object> goodsMap = (Map<String, Object>) map.get("data");
        Assertions.assertNotNull(goodsMap.get("id"));
        Assertions.assertNotNull(goodsMap.get("createdAt"));
        Assertions.assertNotNull(goodsMap.get("updatedAt"));
        Assertions.assertEquals(goodsMap.get("createdAt"), goodsMap.get("updatedAt"));
        Assertions.assertEquals(TEST_GOODS.getName(), goodsMap.get("name"));
        Assertions.assertEquals(TEST_GOODS.getDescription(), goodsMap.get("description"));
        Assertions.assertEquals(TEST_GOODS.getDetails(), goodsMap.get("details"));
        Assertions.assertEquals(TEST_GOODS.getImgUrl(), goodsMap.get("imgUrl"));
        Assertions.assertEquals(TEST_GOODS.getPrice(), ((Integer) goodsMap.get("price")).longValue());
        Assertions.assertEquals(TEST_GOODS.getStock(), (goodsMap.get("stock")));
        Assertions.assertEquals(TEST_GOODS.getShopId(), (goodsMap.get("shopId")));
    }

    @Test
    @Order(4)
    public void successfulUpdateGoods() throws IOException {
        String responseString = initializeHTTPRequest(HTTP_PATCH, "/api/v1/goods/" + GOODS_ID,
                "", UPDATED_GOODS, HttpStatus.OK.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        Map<String, Object> goodsMap = (Map<String, Object>) map.get("data");
        Assertions.assertNotNull(goodsMap.get("id"));
        Assertions.assertNotNull(goodsMap.get("createdAt"));
        Assertions.assertNotNull(goodsMap.get("updatedAt"));
        Assertions.assertNotEquals(goodsMap.get("createdAt"), goodsMap.get("updatedAt"));
        Assertions.assertEquals(UPDATED_GOODS.getName(), goodsMap.get("name"));
        Assertions.assertEquals(UPDATED_GOODS.getDescription(), goodsMap.get("description"));
        Assertions.assertEquals(TEST_GOODS.getDetails(), goodsMap.get("details"));
        Assertions.assertEquals(TEST_GOODS.getImgUrl(), goodsMap.get("imgUrl"));
        Assertions.assertEquals(TEST_GOODS.getPrice(), ((Integer) goodsMap.get("price")).longValue());
        Assertions.assertEquals(TEST_GOODS.getStock(), (goodsMap.get("stock")));
        Assertions.assertEquals(TEST_GOODS.getShopId(), (goodsMap.get("shopId")));
        logoutAfterFunctionalTest();
    }

    @Test
    @Order(5)
    public void forbiddenUpdateGoods() throws IOException {
        anotherUserLoginBeforeFunctionalTest();
        Goods updatedGoods = new Goods();
        updatedGoods.setName("新款肥皂");
        updatedGoods.setDescription("纳米高科技");
        String responseString = initializeHTTPRequest(HTTP_PATCH, "/api/v1/goods/" + GOODS_ID,
                "", updatedGoods, HttpStatus.FORBIDDEN.value());
        logoutAfterFunctionalTest();
    }

    @Test
    @Order(6)
    public void testDeleteGoods() throws IOException {
        loginBeforeFunctionalTest();
        String responseString = initializeHTTPRequest(HTTP_DELETE, "/api/v1/goods/" + GOODS_ID,
                "", null, HttpStatus.NO_CONTENT.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        Map<String, Object> goodsMap = (Map<String, Object>) map.get("data");
        Assertions.assertNotNull(goodsMap.get("id"));
        Assertions.assertNotNull(goodsMap.get("createdAt"));
        Assertions.assertNotNull(goodsMap.get("updatedAt"));
        Assertions.assertNotEquals(goodsMap.get("createdAt"), goodsMap.get("updatedAt"));
        Assertions.assertEquals(UPDATED_GOODS.getName(), goodsMap.get("name"));
        Assertions.assertEquals(UPDATED_GOODS.getDescription(), goodsMap.get("description"));
        Assertions.assertEquals(TEST_GOODS.getDetails(), goodsMap.get("details"));
        Assertions.assertEquals(TEST_GOODS.getImgUrl(), goodsMap.get("imgUrl"));
        Assertions.assertEquals(TEST_GOODS.getPrice(), ((Integer) goodsMap.get("price")).longValue());
        Assertions.assertEquals(TEST_GOODS.getStock(), (goodsMap.get("stock")));
        Assertions.assertEquals(TEST_GOODS.getShopId(), (goodsMap.get("shopId")));
        logoutAfterFunctionalTest();
    }

    @Test
    @Order(7)
    public void getGoodsAfterDelete() throws IOException {
        String responseString = initializeHTTPRequest(HTTP_GET, "/api/v1/goods/" + GOODS_ID,
                "", null, HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Order(8)
    public void testGetPagedGoods() throws IOException {
        // 取一个随机整数，循环插入商品，分页查询验证是否分页成功
        Integer totalGoodsNum = 8;
        for (int i = 0; i < totalGoodsNum; i++) {
            successfulCreateGoods();
        }
        String responseString = initializeHTTPRequest(HTTP_GET, "/api/v1/goods",
                "?pageNum=2&pageSize=3", null, HttpStatus.OK.value());
        Map map = objectMapper.readValue(responseString, Map.class);
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        Integer totalPage = (Integer) map.get("totalPage");
        Assertions.assertEquals(pageNum, 2);
        Assertions.assertEquals(pageSize, 3);
        Assertions.assertEquals(totalPage, 3);
        List<Map<String, Object>> goodsMapList = (List<Map<String, Object>>) map.get("data");
        Map<String, Object> goodsMap = goodsMapList.get(0);
        Assertions.assertNotNull(goodsMap.get("id"));
        Assertions.assertNotNull(goodsMap.get("createdAt"));
        Assertions.assertNotNull(goodsMap.get("updatedAt"));
        Assertions.assertEquals(goodsMap.get("createdAt"), goodsMap.get("updatedAt"));
        Assertions.assertEquals(TEST_GOODS.getName(), goodsMap.get("name"));
        Assertions.assertEquals(TEST_GOODS.getDescription(), goodsMap.get("description"));
        Assertions.assertEquals(TEST_GOODS.getDetails(), goodsMap.get("details"));
        Assertions.assertEquals(TEST_GOODS.getImgUrl(), goodsMap.get("imgUrl"));
        Assertions.assertEquals(TEST_GOODS.getPrice(), ((Integer) goodsMap.get("price")).longValue());
        Assertions.assertEquals(TEST_GOODS.getStock(), (goodsMap.get("stock")));
        Assertions.assertEquals(TEST_GOODS.getShopId(), (goodsMap.get("shopId")));
    }
}
