package com.github.NervousOrange.wxshop.entity;

import com.github.NervousOrange.wxshop.generated.Shop;

import java.util.List;

public class ShoppingCartData {

    private Shop shop;

    private List<ShoppingCartGoods> goods;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<ShoppingCartGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<ShoppingCartGoods> goods) {
        this.goods = goods;
    }
}
