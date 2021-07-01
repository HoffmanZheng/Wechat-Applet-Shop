package com.github.NervousOrange.wxshop.entity;

import com.github.NervousOrange.wxshop.generated.Shop;

import java.util.List;

public class SingleShoppingCart {

    private Shop shop;

    private List<ShoppingCartGoods> goodsList;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<ShoppingCartGoods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ShoppingCartGoods> goodsList) {
        this.goodsList = goodsList;
    }
}
