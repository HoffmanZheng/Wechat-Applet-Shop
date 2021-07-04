package com.github.NervousOrange.wxshop.dao;

import com.github.NervousOrange.wxshop.entity.ShoppingCartData;
import com.github.NervousOrange.wxshop.entity.ShoppingCartGoods;
import com.github.NervousOrange.wxshop.generated.Goods;
import com.github.NervousOrange.wxshop.generated.Shop;
import com.github.NervousOrange.wxshop.generated.ShoppingCart;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShoppingCartQuery {
    int countShopInShoppingCart(@Param("userId") int userId);

    List<Shop> getPagedShoppingCartShop(
            @Param("userId") int userId,
            @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<ShoppingCartGoods> getPagedShoppingCartGoodsList(
            @Param("userId") int userId,
            @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<Goods> getShopIdByGoodsIdList(@Param("goodsIdList") List<Integer> goodsIdList);

    void insertShoppingCartList(@Param("shoppingCartList") List<ShoppingCart> shoppingCartList);

    List<ShoppingCartData> getShoppingCartDataByUserIdAndShopId(@Param("userId") Integer userId, @Param("shopId") Integer shopId);
}
