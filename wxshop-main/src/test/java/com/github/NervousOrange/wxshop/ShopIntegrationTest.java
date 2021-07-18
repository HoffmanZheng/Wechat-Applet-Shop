package com.github.NervousOrange.wxshop;

import com.github.NervousOrange.wxshop.generated.Shop;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@ExtendWith(SpringExtension.class)  // Spring 为 JUnit 5 提供的插件，可以在测试中使用 Spring 相关的功能，依赖注入等
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // @BeforeAll 需要是 PER_CLASS
// Shared test instance state between test methods as well as between non-static @BeforeAll and @AfterAll methods in the test class.
public class ShopIntegrationTest extends AbstractIntegrationTest{

    protected static final Shop TEST_SHOP = new Shop();

    static {
        TEST_SHOP.setName("我的店铺");
        TEST_SHOP.setDescription("我的苹果专卖店");
        TEST_SHOP.setImgUrl("https://img.url");
    }

    @BeforeAll
    public void initDatabase() {
        initDataBase();
    }

    @Test
    public void testCreateShop() throws IOException {
        loginBeforeFunctionalTest();
    }
}
