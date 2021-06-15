package com.github.NervousOrange.wxshop.controller;

import com.github.NervousOrange.wxshop.entity.Response;
import com.github.NervousOrange.wxshop.generated.Shop;
import com.github.NervousOrange.wxshop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class ShopController {

    private ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    /**
     * @api {post} /shop 创建店铺
     * @apiSampleRequest off
     * @apiName createShop
     * @apiGroup 店铺
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParamExample {json} Request-Example:
     *          {
     *              "name": "肥皂",
     *              "description": "纯天然无污染肥皂",
     *              "imgUrl": "https://img.url",
     *          }
     *
     * @apiSuccess {Shop} data 创建的店铺
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 201 Created
     *     {
     *       "data": {
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
     *              "imgUrl": "https://img.url",
     *              "ownerUserId": 12345,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *       }
     *     }
     * @apiError 400 Bad Request 若用户的请求包含错误
     * @apiError 401 Unauthorized 若用户未登录
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     *
     */
    @PostMapping("/shop")
    public Response<Shop> createShop(@RequestBody Shop shop) {
        shop.setId(null);
        shop.setCreatedAt(new Date());
        shop.setUpdatedAt(new Date());
        Shop result = shopService.createShop(shop);
        return Response.of(result, null);
    }
}
