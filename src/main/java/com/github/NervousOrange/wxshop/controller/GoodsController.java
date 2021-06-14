package com.github.NervousOrange.wxshop.controller;

import com.github.NervousOrange.wxshop.common.exception.DataNotFoundException;
import com.github.NervousOrange.wxshop.common.exception.ShopNotAuthorizedException;
import com.github.NervousOrange.wxshop.entity.Response;
import com.github.NervousOrange.wxshop.generated.Goods;
import com.github.NervousOrange.wxshop.service.GoodsService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class GoodsController {

    private GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /**
     * @api {post} /goods 创建商品
     * @apiSampleRequest off
     * @apiName createGoods
     * @apiGroup 商品
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParamExample {json} Request-Example:
     *          {
     *              "name": "肥皂",
     *              "description": "纯天然无污染肥皂",
     *              "details": "这是一块好肥皂",
     *              "imgUrl": "https://img.url",
     *              "price": 500,
     *              "stock": 10,
     *              "shopId": 12345
     *          }
     *
     * @apiSuccess {Goods} data 创建的商品
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 201 Created
     *     {
     *       "data": {
     *              "id": 12345,
     *              "name": "肥皂",
     *              "description": "纯天然无污染肥皂",
     *              "details": "这是一块好肥皂",
     *              "imgUrl": "https://img.url",
     *              "price": 500,
     *              "stock": 10,
     *              "shopId": 12345,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *       }
     *     }
     * @apiError 400 Bad Request 若用户的请求包含错误
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 403 Forbidden 若用户尝试创建非自己管理店铺的商品
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     *
     */
    @PostMapping("/goods")
    public Response<Goods> createGoods(@RequestBody Goods goods, HttpServletResponse response) {
        // 对于前端传过来的 id createdAt updatedAt 要忽略掉
        goods.setId(null);
        goods.setCreatedAt(new Date());
        goods.setUpdatedAt(new Date());
        try {
            Goods result = goodsService.createGoods(goods);
            response.setStatus(HttpStatus.CREATED.value());
            return Response.of(result, null);
        } catch (ShopNotAuthorizedException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return Response.of(null, e.getMessage());
        }
    }

    /**
     * @api {post} /goods/:id 删除商品
     * @apiSampleRequest off
     * @apiName deleteGoods
     * @apiGroup 商品
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {Number} id 商品 ID
     *
     * @apiSuccess {Goods} data 被删除的商品
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 204 No Content
     *     {
     *       "data": {
     *              "id": 12345,
     *              "name": "肥皂",
     *              "description": "纯天然无污染肥皂",
     *              "details": "这是一块好肥皂",
     *              "imgUrl": "https://img.url",
     *              "price": 500,
     *              "stock": 10,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *       }
     *     }
     * @apiError 400 Bad Request 若用户的请求包含错误
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 403 Forbidden 若用户尝试创建非自己管理店铺的商品
     * @apiError 404 Not Found 若商品未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    @DeleteMapping("/goods/{id}")
    public Response<Goods> deleteGoods(@PathVariable("id") Integer goodsId, HttpServletResponse response) {
        try {
            Goods goods = goodsService.deleteGoodsById(goodsId);
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return Response.of(goods, null);
        } catch (ShopNotAuthorizedException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return Response.of(null, e.getMessage());
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return Response.of(null, e.getMessage());
        }
    }

    /**
     * @api {patch} /goods/:id 更新商品
     * @apiSampleRequest off
     * @apiName updateGoods
     * @apiGroup 商品
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {Number} id 商品 ID
     *
     * @apiParamExample {json} Request-Example:
     *          {
     *              "name": "肥皂",
     *              "description": "纯天然无污染肥皂",
     *              "details": "这是一块好肥皂",
     *              "imgUrl": "https://img.url",
     *              "price": 500,
     *              "stock": 10,
     *          }
     *
     * @apiSuccess {Goods} data 创建的商品
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "data": {
     *              "id": 12345,
     *              "name": "肥皂",
     *              "description": "纯天然无污染肥皂",
     *              "details": "这是一块好肥皂",
     *              "imgUrl": "https://img.url",
     *              "price": 500,
     *              "stock": 10,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *       }
     *     }
     * @apiError 400 Bad Request 若用户的请求包含错误
     * @apiError 401 Unauthorized 若用户未登录
     * @apiError 403 Forbidden 若用户尝试创建非自己管理店铺的商品
     * @apiError 404 Not Found 若商品未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    @PatchMapping("/goods/{id}")
    public Response<Goods> updateGoods(@PathVariable("id") Integer goodsId,
                             @RequestBody Goods goods, HttpServletResponse response) {
        try {
            Goods result = goodsService.updateGoodsById(goodsId, goods);
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
     * @api {post} /goods 获取所有商品
     * @apiSampleRequest off
     * @apiName getGoodsList
     * @apiGroup 商品
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiParam {Number} pageNum 页数，从1开始
     * @apiParam {Number} pageSize 每页显示的数量
     * @apiParam {Number} [shopId] 店铺 ID
     *
     * @apiSuccess {Goods} data 商品列表
     * @apiSuccess {Number} pageNum 页数，从1开始
     * @apiSuccess {Number} pageSize 每页显示的数量
     * @apiSuccess {Number} totalPage 共有多少页
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *      {
     *          "pageNum": 1,
     *          "pageSize": 10,
     *          "totalPage": 5,
     *          "data": [
     *          {
     *              "id": 12345,
     *              "name": "肥皂",
     *              "description": "纯天然无污染肥皂",
     *              "details": "这是一块好肥皂",
     *              "imgUrl": "https://img.url",
     *              "price": 500,
     *              "stock": 10,
     *              "shopId": 12345,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *          },
     *          {
     *              ...
     *          }
     *          ]
     *      }
     *
     * @apiError 401 Unauthorized 若用户未登录
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    @GetMapping("/goods")
    public PageInfo<Goods> getGoodsList(
            @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "shopId", required = false) Integer shopId) {
        return goodsService.getGoodsList(pageNum, pageSize, shopId);
    }

    /**
     * @api {post} /goods/:id 获取指定商品
     * @apiSampleRequest off
     * @apiName getGoodsById
     * @apiGroup 商品
     *
     * @apiHeader {String} Accept application/json
     * @apiHeader {String} Content-Type application/json
     *
     * @apiSuccessExample Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *       "data": {
     *              "id": 12345,
     *              "name": "肥皂",
     *              "description": "纯天然无污染肥皂",
     *              "details": "这是一块好肥皂",
     *              "imgUrl": "https://img.url",
     *              "price": 500,
     *              "stock": 10,
     *              "shopId": 12345,
     *              "createdAt": "2020-03-22T13:22:03Z",
     *              "updatedAt": "2020-03-22T13:22:03Z"
     *       }
     *     }
     *
     * @apiError 400 Bad Request 若用户的请求包含错误
     * @apiError 404 Not Found 商品未找到
     *
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *       "message": "Unauthorized"
     *     }
     */
    @GetMapping("/goods/{id}")
    public Response<Goods> getGoodsById(@PathVariable("id") Integer goodsId, HttpServletResponse response) {
        try {
            Goods goods = goodsService.getGoodsById(goodsId);
            return Response.of(goods, null);
        } catch (DataNotFoundException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return Response.of(null, e.getMessage());
        }
    }
}
