package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.api.IOrderService;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "${wxshop.order.version}")
public class OrderServiceImpl implements IOrderService {

    @Override
    public String testRPC(String name) {
        System.out.println("get rpc, name: " + name);
        return null;
    }
}
