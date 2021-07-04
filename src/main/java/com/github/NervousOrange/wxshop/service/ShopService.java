package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.common.exception.HttpException;
import com.github.NervousOrange.wxshop.entity.PagedResponse;
import com.github.NervousOrange.wxshop.generated.Shop;
import com.github.NervousOrange.wxshop.generated.ShopExample;
import com.github.NervousOrange.wxshop.generated.ShopMapper;
import com.github.NervousOrange.wxshop.generated.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.github.NervousOrange.wxshop.common.constant.StringConstants.*;

@Service
public class ShopService {

    private final ShopMapper shopMapper;

    @Autowired
    public ShopService(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    public Shop createShop(Shop shop) {
        shop.setStatus(STATUS_CREATED);
        shopMapper.insertSelective(shop);
        return shop;
    }

    public Shop updateShopById(Integer userId, Shop shop) {
        Shop shopInDatabase = shopMapper.selectByPrimaryKey(shop.getId());
        if (shopInDatabase == null) {
            throw HttpException.notFound(SHOP_NOT_FOUND);
        }
        if (!shopInDatabase.getOwnerUserId().equals(userId)) {
            throw HttpException.forbidden(SHOP_NOT_AUTHORIZED);
        }
        shop.setUpdatedAt(new Date());
        shopMapper.updateByPrimaryKeySelective(shop);
        return shopMapper.selectByPrimaryKey(shop.getId());
    }

    public Shop deleteShopById(Integer shopId, Integer userId) {
        Shop shopInDatabase = shopMapper.selectByPrimaryKey(shopId);
        if (shopInDatabase == null) {
            throw HttpException.notFound(SHOP_NOT_FOUND);
        }
        if (!shopInDatabase.getOwnerUserId().equals(userId)) {
            throw HttpException.forbidden(SHOP_NOT_AUTHORIZED);
        }
        Shop shop = new Shop();
        shop.setId(shopId);
        shop.setStatus(STATUS_DELETED);
        shopMapper.updateByPrimaryKeySelective(shop);
        return shopMapper.selectByPrimaryKey(shopId);
    }

    public Shop getShopById(Integer shopId) {
        ShopExample example = new ShopExample();
        example.createCriteria().andIdEqualTo(shopId).andStatusEqualTo(STATUS_CREATED);
        List<Shop> shops = shopMapper.selectByExample(example);
        if (shops == null || shops.size() == 0) {
            throw HttpException.notFound(SHOP_NOT_FOUND);
        } else {
            return shops.get(0);
        }
    }

    public PagedResponse<List<Shop>> getShopList(Integer pageNum, Integer pageSize, User currentUser) {
        Integer userId = currentUser.getId();
        ShopExample example = new ShopExample();
        example.createCriteria().andOwnerUserIdEqualTo(userId).andStatusNotEqualTo(STATUS_DELETED);
        long shopCount = shopMapper.countByExample(example);
        Integer totalPage = Math.toIntExact(shopCount / pageSize) + 1;
        Integer offset = (pageNum - 1) * pageSize;
        Integer limit = pageSize;
        example.setOffset(offset);
        example.setLimit(limit);
        List<Shop> shops = shopMapper.selectByExample(example);
        return new PagedResponse<>(pageNum, pageSize, totalPage, shops);
    }
}
