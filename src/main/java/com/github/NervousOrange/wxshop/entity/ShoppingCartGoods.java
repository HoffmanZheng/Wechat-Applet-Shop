package com.github.NervousOrange.wxshop.entity;

import com.github.NervousOrange.wxshop.generated.Goods;

public class ShoppingCartGoods extends Goods {

    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
