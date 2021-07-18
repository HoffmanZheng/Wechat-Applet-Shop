package com.github.NervousOrange.wxshop.controller;

import com.github.NervousOrange.wxshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@ResponseBody
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/testRpc")
    public String testRpc(String name) {
        return orderService.testRpc(name);
    }

}
