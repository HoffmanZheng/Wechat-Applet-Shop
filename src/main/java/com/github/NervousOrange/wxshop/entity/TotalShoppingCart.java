package com.github.NervousOrange.wxshop.entity;

import java.util.List;

public class TotalShoppingCart {

    private List<SingleShoppingCart> shoppingCartList;

    public List<SingleShoppingCart> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(List<SingleShoppingCart> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }
}
