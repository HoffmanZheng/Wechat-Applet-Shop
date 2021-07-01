package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.entity.SingleShoppingCart;
import com.github.NervousOrange.wxshop.generated.ShoppingCartMapper;
import com.github.NervousOrange.wxshop.generated.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    private final ShoppingCartMapper shoppingCartMapper;

    @Autowired
    public ShoppingCartService(ShoppingCartMapper shoppingCartMapper) {
        this.shoppingCartMapper = shoppingCartMapper;
    }

    public SingleShoppingCart deleteGoodsInShoppingCart(User currentUser, Integer goodsId) {
        return null;
    }
}
