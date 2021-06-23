package com.github.NervousOrange.wxshop.controller;

import com.github.NervousOrange.wxshop.common.exception.DataNotFoundException;
import com.github.NervousOrange.wxshop.common.exception.ShopNotAuthorizedException;
import com.github.NervousOrange.wxshop.config.UserContext;
import com.github.NervousOrange.wxshop.entity.Response;
import com.github.NervousOrange.wxshop.entity.SingleShoppingCart;
import com.github.NervousOrange.wxshop.generated.Shop;
import com.github.NervousOrange.wxshop.generated.User;
import com.github.NervousOrange.wxshop.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
@ResponseBody
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * @api {delete} /shoppingCart/:goodsId 删除当前购物车某个商品
     * @apiSampleRequest off
     * @apiName deleteGoodsInShoppingCart
     * @apiGroup 购物车
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {Number} goodsId 要删除的商品 ID
     *
     * @apiSuccess {ShoppingCart} data 更新后的该店铺商品列表
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {
     *          "data": {
     *              "shop": {
     *                  "id": 12345,
     *                  "name": "我的店铺",
     *                  "description": "我的苹果专卖店",
     *                  "imgUrl": "https://img.url",
     *                  "ownerUserId": 12345,
     *                  "createdAt": "2020-03-22T13:22:03Z",
     *                  "updatedAt": "2020-03-22T13:22:03Z"
     *              },
     *              "goods":[{
     *                  "id": 12345,
     *                  "name": "肥皂",
     *                  "description": "纯天然无污染肥皂",
     *                  "details": "这是一块好肥皂",
     *                  "imgUrl": "https://img.url",
     *                  "address": "XXX",
     *                  "price": 500,
     *                  "number": 10,
     *                  "createdAt": "2020-03-22T13:22:03Z",
     *                  "updatedAt": "2020-03-22T13:22:03Z"
     *              },
     *              {
     *                  ...
     *              }]
     *          }
     *       }
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
    @DeleteMapping("/shoppingCart/{goodsId}")
    public Response<SingleShoppingCart> deleteGoodsInShoppingCart(@PathVariable("goodsId") Integer goodsId, HttpServletResponse response) {
        try {
            User currentUser = UserContext.getCurrentUser();
            SingleShoppingCart singleShoppingCart = shoppingCartService.deleteGoodsInShoppingCart(currentUser, goodsId);
            return Response.of(singleShoppingCart, null);
        } catch (ShopNotAuthorizedException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return Response.of(null, e.getMessage());
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return Response.of(null, e.getMessage());
        }
    }

    /**
     * @api {post} /shoppingCart 商品加购物车
     * @apiSampleRequest off
     * @apiName addGoodsInShoppingCart
     * @apiGroup 购物车
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParamExample {json} Request-Example:
     *          {
     *              "goods": [{
     *                      "id": 12345,
     *                      "number": 10,
     *                  },
     *                  {
     *                      ...
     *                  }]
     *           }
     *
     * @apiSuccess {ShoppingCart} data 更新后的该店铺商品列表
     *
     * @apiSuccessExample Success-Response:
     *       HTTP/1.1 200 OK
     *       {
     *          "data": {
     *              "shop": {
     *                  "id": 12345,
     *                  "name": "我的店铺",
     *                  "description": "我的苹果专卖店",
     *                  "imgUrl": "https://img.url",
     *                  "ownerUserId": 12345,
     *                  "createdAt": "2020-03-22T13:22:03Z",
     *                  "updatedAt": "2020-03-22T13:22:03Z"
     *              },
     *              "goods":[{
     *                  "id": 12345,
     *                  "name": "肥皂",
     *                  "description": "纯天然无污染肥皂",
     *                  "details": "这是一块好肥皂",
     *                  "imgUrl": "https://img.url",
     *                  "address": "XXX",
     *                  "price": 500,
     *                  "number": 10,
     *                  "createdAt": "2020-03-22T13:22:03Z",
     *                  "updatedAt": "2020-03-22T13:22:03Z"
     *              },
     *              {
     *                  ...
     *              }]
     *          }
     *       }
     *
     * @apiError 400 Bad Request 若用户的请求包含错误
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 404 Not Found 若店铺未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     *
     */
    @PostMapping("/shoppingCart")
    public Response<Shop> addGoodsInShoppingCart(@PathVariable("id") Integer shopId, @RequestBody Shop shop, HttpServletResponse response) {
        try {
            return null;
        } catch (ShopNotAuthorizedException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return Response.of(null, e.getMessage());
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return Response.of(null, e.getMessage());
        }
    }

    /**
     * @api {get} /shoppingCart 获取购物车商品列表
     * @apiSampleRequest off
     * @apiName getGoodsListInShoppingCart
     * @apiGroup 购物车
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {Number} pageNum 页数，从1开始
     * @apiParam {Number} pageSize 每页显示的数量
     *
     * @apiSuccess {Number} pageNum 页数，从1开始
     * @apiSuccess {Number} pageSize 每页显示的数量
     * @apiSuccess {Number} totalPage 共有多少页
     * @apiSuccess {GoodsList} data 购物车物品列表，按照店铺分组
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *          {
     *              "pageNum": 1,
     *              "pageSize": 10,
     *              "totalPage": 5,
     *              "data": [{
     *                   "shop": {
     *                      "id": 12345,
     *                      "name": "我的店铺",
     *                      "description": "我的苹果专卖店",
     *                      "imgUrl": "https://img.url",
     *                      "ownerUserId": 12345,
     *                      "createdAt": "2020-03-22T13:22:03Z",
     *                      "updatedAt": "2020-03-22T13:22:03Z"
     *                    },
     *                    "goods":[{
     *                       "id": 12345,
     *                       "name": "肥皂",
     *                       "description": "纯天然无污染肥皂",
     *                       "details": "这是一块好肥皂",
     *                       "imgUrl": "https://img.url",
     *                       "address": "XXX",
     *                       "price": 500,
     *                       "number": 10,
     *                       "createdAt": "2020-03-22T13:22:03Z",
     *                       "updatedAt": "2020-03-22T13:22:03Z"
     *                    },
     *                    {
     *                        ...
     *                    }]
     *                }]
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
    @PatchMapping("/shoppingCart")
    public Response<Shop> getGoodsListInShoppingCart(@PathVariable("id") Integer shopId, @RequestBody Shop shop, HttpServletResponse response) {
        try {
            return null;
        } catch (ShopNotAuthorizedException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return Response.of(null, e.getMessage());
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return Response.of(null, e.getMessage());
        }
    }
}
