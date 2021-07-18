package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.api.IOrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Reference(version = "${order.service.version}")
    private IOrderService iOrderService;

    public String testRpc(String name) {
        return iOrderService.testRPC(name);
    }
}
