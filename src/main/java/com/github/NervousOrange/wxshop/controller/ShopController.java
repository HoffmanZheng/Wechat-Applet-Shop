package com.github.NervousOrange.wxshop.controller;

import com.github.NervousOrange.wxshop.common.exception.DataNotFoundException;
import com.github.NervousOrange.wxshop.common.exception.ShopNotAuthorizedException;
import com.github.NervousOrange.wxshop.config.UserContext;
import com.github.NervousOrange.wxshop.entity.PagedResponse;
import com.github.NervousOrange.wxshop.entity.Response;
import com.github.NervousOrange.wxshop.generated.Shop;
import com.github.NervousOrange.wxshop.generated.User;
import com.github.NervousOrange.wxshop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@ResponseBody
public class ShopController {

    private ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    /**
     * @api {patch} /shop/:id 修改店铺
     * @apiSampleRequest off
     * @apiName updateShop
     * @apiGroup 店铺
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {Number} id 店铺 ID
     *
     * @apiParamExample {json} Request-Example:
     *          {
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
     *              "imgUrl": "https://img.url",
     *          }
     *
     * @apiSuccess {Shop} data 修改后的店铺
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
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
     * @apiError 403 Forbidden 若用户尝试修改非自己管理店铺
     * @apiError 404 Not Found 若店铺未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     *
     */
    @PatchMapping("/shop/{id}")
    public Response<Shop> updateShop(@PathVariable("id") Integer shopId, @RequestBody Shop shop, HttpServletResponse response) {
        try {
            Shop result = shopService.updateShopById(shopId, shop);
            return Response.of(result, null);
        } catch (ShopNotAuthorizedException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return Response.of(null, e.getMessage());
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return Response.of(null, e.getMessage());
        }
    }

    /**
     * @api {delete} /shop/:id 删除店铺
     * @apiSampleRequest off
     * @apiName deleteShop
     * @apiGroup 店铺
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {Number} id 店铺 ID
     *
     * @apiSuccess {Shop} data 删除的店铺
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 204 No Content
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
     * @apiError 403 Forbidden 若用户尝试修改非自己管理店铺
     * @apiError 404 Not Found 若店铺未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     *
     */
    @DeleteMapping("/shop/{id}")
    public Response<Shop> deleteShop(@PathVariable("id") Integer shopId, HttpServletResponse response) {
        try {
            Shop result = shopService.deleteShopById(shopId);
            return Response.of(result, null);
        } catch (ShopNotAuthorizedException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return Response.of(null, e.getMessage());
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return Response.of(null, e.getMessage());
        }
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
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
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
        User currentUser = UserContext.getCurrentUser();
        shop.setId(null);
        shop.setCreatedAt(new Date());
        shop.setUpdatedAt(new Date());
        shop.setOwnerUserId(currentUser.getId());
        Shop result = shopService.createShop(shop);
        return Response.of(result, null);
    }

    /**
     * @api {get} /shop 获取店铺列表
     * @apiSampleRequest off
     * @apiName getShopList
     * @apiGroup 店铺
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParamExample {json} Request-Example:
     *          {
     *              "id": 12345,
     *              "name": "我的店铺",
     *              "description": "我的苹果专卖店",
     *              "imgUrl": "https://img.url",
     *          }
     *
     * @apiSuccess {Number} pageNum 页数，从1开始
     * @apiSuccess {Number} pageSize 每页显示的数量
     * @apiSuccess {Number} totalPage 共有多少页
     * @apiSuccess {ShopList} data 店铺列表
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *          {
     *              "pageNum": 1,
     *              "pageSize": 10,
     *              "totalPage": 5,
     *              "data": [
     *              {
     *                  "id": 12345,
     *                  "name": "我的店铺",
     *                  "description": "我的苹果专卖店",
     *                  "imgUrl": "https://img.url",
     *                  "ownerUserId": 12345,
     *                  "createdAt": "2020-03-22T13:22:03Z",
     *                  "updatedAt": "2020-03-22T13:22:03Z"
     *              },
     *              {
     *                  ...
     *              }]
     *          }
     *
     * @apiError 401 Unauthorized 若用户未登录
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     *
     */
    @GetMapping("/shop")
    public PagedResponse<List<Shop>> getShopList(@RequestParam("pageNum") Integer pageNum,
                                                @RequestParam("pageSize") Integer pageSize) {
        User currentUser = UserContext.getCurrentUser();
        PagedResponse<List<Shop>> result = shopService.getShopList(pageNum, pageSize, currentUser);
        return result;
    }

    /**
     * @api {get} /shop/:id 获取指定的店铺
     * @apiSampleRequest off
     * @apiName getShopById
     * @apiGroup 店铺
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {Number} id 店铺 ID
     *
     * @apiSuccess {Shop} data 指定 ID 的店铺
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
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
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 404 Not found 若店铺未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     *
     */
    @GetMapping("/shop/{id}")
    public Response<Shop> getShopById(@PathVariable("id") Integer shopId, HttpServletResponse response) {
        try {
            Shop shop = shopService.getShopById(shopId);
            return Response.of(shop, null);
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return Response.of(null, e.getMessage());
        }
    }
}
