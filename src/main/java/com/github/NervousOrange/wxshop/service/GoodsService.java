package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.common.exception.DataNotFoundException;
import com.github.NervousOrange.wxshop.common.exception.ShopNotAuthorizedException;
import com.github.NervousOrange.wxshop.config.UserContext;
import com.github.NervousOrange.wxshop.generated.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.github.NervousOrange.wxshop.common.constant.StringConstants.*;

@Service
public class GoodsService {

    private final ShopMapper shopMapper;
    private final GoodsMapper goodsMapper;

    @Autowired
    public GoodsService(ShopMapper shopMapper, GoodsMapper goodsMapper) {
        this.shopMapper = shopMapper;
        this.goodsMapper = goodsMapper;
    }

    public Goods createGoods(Goods goods) {
        // 对 shopId 做校验，请求者需要是店铺的所有者
        Shop shop = shopMapper.selectByPrimaryKey(goods.getShopId());
        if (!shop.getOwnerUserId().equals(UserContext.getCurrentUser().getId())) {
            throw new ShopNotAuthorizedException(SHOP_NOT_AUTHORIZED);
        } else {
            goods.setStatus(STATUS_CREATED);
            int id = goodsMapper.insertSelective(goods);
            goods.setId(id);
            return goods;
        }
    }

    public Goods deleteGoodsById(Integer goodsId) {
        GoodsExample example = new GoodsExample();
        example.createCriteria().andIdEqualTo(goodsId).andStatusNotEqualTo(STATUS_DELETED);
        List<Goods> goodsList = goodsMapper.selectByExample(example);
        if (goodsList == null || goodsList.isEmpty()) {
            throw new DataNotFoundException(GOODS_NOT_FOUND);
        }
        // 对 shopId 做校验，请求者需要是店铺的所有者
        Goods goodsById = goodsList.get(0);
        Shop shop = shopMapper.selectByPrimaryKey(goodsById.getShopId());
        if (!shop.getOwnerUserId().equals(UserContext.getCurrentUser().getId())) {
            throw new ShopNotAuthorizedException(SHOP_NOT_AUTHORIZED);
        } else {
            goodsById.setStatus(STATUS_DELETED);
            goodsById.setUpdatedAt(new Date());
            goodsMapper.updateByPrimaryKey(goodsById);
            return goodsById;
        }
    }

    public Goods updateGoodsById(Integer goodsId, Goods goods) {
        GoodsExample example = new GoodsExample();
        example.createCriteria().andIdEqualTo(goodsId).andStatusNotEqualTo(STATUS_DELETED);
        List<Goods> goodsList = goodsMapper.selectByExample(example);
        if (goodsList == null || goodsList.isEmpty()) {
            throw new DataNotFoundException(GOODS_NOT_FOUND);
        }
        // 对 shopId 做校验，请求者需要是店铺的所有者
        Goods goodsById = goodsList.get(0);
        Shop shop = shopMapper.selectByPrimaryKey(goodsById.getShopId());
        if (!shop.getOwnerUserId().equals(UserContext.getCurrentUser().getId())) {
            throw new ShopNotAuthorizedException(SHOP_NOT_AUTHORIZED);
        } else {
            goodsById.setName(goods.getName());
            goodsById.setDetails(goods.getDetails());
            goodsById.setDescription(goods.getDescription());
            goodsById.setImgUrl(goods.getImgUrl());
            goodsById.setPrice(goods.getPrice());
            goodsById.setStock(goods.getStock());
            goodsById.setUpdatedAt(new Date());
            goodsMapper.updateByPrimaryKeySelective(goodsById);
            return goodsById;
        }
    }

    public Goods getGoodsById(Integer goodsId) {
        GoodsExample example = new GoodsExample();
        example.createCriteria().andIdEqualTo(goodsId).andStatusNotEqualTo(STATUS_DELETED);
        List<Goods> goodsList = goodsMapper.selectByExample(example);
        if (goodsList == null || goodsList.isEmpty()) {
            throw new DataNotFoundException(GOODS_NOT_FOUND);
        }
        return goodsList.get(0);
    }

    public PageInfo<Goods> getGoodsList(Integer pageNum, Integer pageSize, Integer shopId) {
        PageHelper.startPage(pageNum, pageSize);
        GoodsExample example = new GoodsExample();
        if (shopId != null) {
            example.createCriteria().andShopIdEqualTo(shopId).andStatusNotEqualTo(STATUS_DELETED);
        }
        List<Goods> goodsList = goodsMapper.selectByExample(example);
        return new PageInfo<>(goodsList);
    }
}
