package com.github.NervousOrange.wxshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.NervousOrange.wxshop.common.exception.HttpException;
import com.github.NervousOrange.wxshop.controller.ShoppingCartController;
import com.github.NervousOrange.wxshop.dao.ShoppingCartQuery;
import com.github.NervousOrange.wxshop.entity.PagedResponse;
import com.github.NervousOrange.wxshop.entity.Response;
import com.github.NervousOrange.wxshop.entity.ShoppingCartData;
import com.github.NervousOrange.wxshop.entity.ShoppingCartGoods;
import com.github.NervousOrange.wxshop.generated.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.NervousOrange.wxshop.common.constant.StringConstants.*;

@Service
public class ShoppingCartService {

    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartQuery shoppingCartQuery;
    private final GoodsMapper goodsMapper;

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ShoppingCartService(ShoppingCartMapper shoppingCartMapper, ShoppingCartQuery shoppingCartQuery, GoodsMapper goodsMapper) {
        this.shoppingCartMapper = shoppingCartMapper;
        this.shoppingCartQuery = shoppingCartQuery;
        this.goodsMapper = goodsMapper;
    }

    public ShoppingCartData deleteGoodsInShoppingCart(User currentUser, Integer goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        if (goods == null) {
            throw new RuntimeException("商品未找到：" + goodsId);
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setStatus(STATUS_DELETED);
        shoppingCart.setUpdatedAt(new Date());
        ShoppingCartExample example = new ShoppingCartExample();
        example.createCriteria().andUserIdEqualTo(currentUser.getId())
                .andGoodsIdEqualTo(goodsId);
        shoppingCartMapper.updateByExampleSelective(shoppingCart, example);
        List<ShoppingCartData> shoppingCartDataList = shoppingCartQuery.getShoppingCartDataByUserIdAndShopId(currentUser.getId(), goods.getShopId());
        if (shoppingCartDataList == null || shoppingCartDataList.size() == 0) {
            return new ShoppingCartData();
        } else {
            return mergeShoppingCartDataList(shoppingCartDataList);
        }
    }

    public PagedResponse<List<ShoppingCartData>> getGoodsListInShoppingCartOfUser(int userId, int pageNum, int pageSize) throws JsonProcessingException {
        // 按照店铺分页，先获取共有多少结果，多少个店铺
        int shopCountInShoppingCart = shoppingCartQuery.countShopInShoppingCart(userId);
        int totalPage = Math.toIntExact(shopCountInShoppingCart / pageSize) + 1;
        int offset = (pageNum - 1) * pageSize;
        List<Shop> shopList = shoppingCartQuery.getPagedShoppingCartShop(userId, offset, pageSize);
        List<ShoppingCartGoods> goodsList = shoppingCartQuery.getPagedShoppingCartGoodsList(userId, offset, pageSize);
        Map<Integer, List<ShoppingCartGoods>> collect = goodsList.stream().collect(Collectors.groupingBy(ShoppingCartGoods::getShopId));
        List<ShoppingCartData> result = new ArrayList<>();
        for (Shop shop : shopList) {
            ShoppingCartData shoppingCartData = new ShoppingCartData();
            shoppingCartData.setShop(shop);
            List<ShoppingCartGoods> shoppingCartGoods = collect.get(shop.getId());
            if (shoppingCartGoods != null) {
                shoppingCartData.setGoods(shoppingCartGoods);
            } else {
                shoppingCartData.setGoods(new ArrayList<>());
                logger.info("getGoodsListInShoppingCartOfUser has unmatched goodsList, shop: [{}], goodsListMap: [{}}]",
                        objectMapper.writeValueAsString(shop), objectMapper.writeValueAsString(goodsList));
            }
            result.add(shoppingCartData);
        }
        return new PagedResponse<>(pageNum, pageSize, totalPage, result);
    }

    public Response<ShoppingCartData> addGoodsInShoppingCart(
            ShoppingCartController.AddToShoppingCartRequest addToShoppingCartRequest, Integer userId) {
        List<ShoppingCartController.AddToShoppingCartItem> requestItemList = addToShoppingCartRequest.getGoods();
        // 去商品表查它的店铺 id
        List<Integer> goodsIdList = new ArrayList<>();
        for (ShoppingCartController.AddToShoppingCartItem item : requestItemList) {
            goodsIdList.add(item.getId());
        }
        if (goodsIdList.isEmpty()) {
            throw HttpException.badRequest(String.format(EMPTY_PARAM, "goodsId"));
        }
        List<Goods> goodsList = shoppingCartQuery.getShopIdByGoodsIdList(goodsIdList);
        if (goodsList == null || goodsList.isEmpty()) {
            throw HttpException.notFound(GOODS_NOT_FOUND);
        }
        Integer shopId = goodsList.get(0).getShopId();
        for (Goods goods : goodsList) {
            if (!goods.getShopId().equals(shopId)) {
                throw new RuntimeException("同时只能添加同一家店铺的商品");
            }
        }
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        for (ShoppingCartController.AddToShoppingCartItem item : requestItemList) {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setGoodsId(item.getId());
            shoppingCart.setNumber(item.getNumber());
            shoppingCart.setUserId(userId);
            shoppingCart.setStatus(STATUS_CREATED);
            shoppingCart.setShopId(shopId);
            shoppingCartList.add(shoppingCart);
        }
        shoppingCartQuery.addOrUpdateShoppingCartList(shoppingCartList);
        List<ShoppingCartData> shoppingCartDataList = shoppingCartQuery.getShoppingCartDataByUserIdAndShopId(userId, shopId);
        ShoppingCartData shoppingCartData = mergeShoppingCartDataList(shoppingCartDataList);
        return Response.of(shoppingCartData, null);
    }

    private ShoppingCartData mergeShoppingCartDataList(List<ShoppingCartData> shoppingCartDataList) {
        Shop shop = shoppingCartDataList.get(0).getShop();
        List<ShoppingCartGoods> shoppingCartGoodsList = shoppingCartDataList.stream().
                map(ShoppingCartData::getGoods).flatMap(List::stream).collect(Collectors.toList());
        ShoppingCartData shoppingCartData = new ShoppingCartData();
        shoppingCartData.setShop(shop);
        shoppingCartData.setGoods(shoppingCartGoodsList);
        return shoppingCartData;
    }
}
