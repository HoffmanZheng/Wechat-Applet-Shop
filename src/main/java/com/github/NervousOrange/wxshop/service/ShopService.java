package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.generated.Shop;
import com.github.NervousOrange.wxshop.generated.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.github.NervousOrange.wxshop.common.constant.StringConstants.STATUS_CREATED;

@Service
public class ShopService {

    private final ShopMapper shopMapper;

    @Autowired
    public ShopService(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    public Shop createShop(Shop shop) {
        shop.setStatus(STATUS_CREATED);
        int id = shopMapper.insertSelective(shop);
        shop.setId(id);
        return shop;
    }
}
