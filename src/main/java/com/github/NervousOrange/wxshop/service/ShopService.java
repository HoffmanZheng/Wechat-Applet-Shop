package com.github.NervousOrange.wxshop.service;

import com.github.NervousOrange.wxshop.entity.PagedResponse;
import com.github.NervousOrange.wxshop.generated.Shop;
import com.github.NervousOrange.wxshop.generated.ShopExample;
import com.github.NervousOrange.wxshop.generated.ShopMapper;
import com.github.NervousOrange.wxshop.generated.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.github.NervousOrange.wxshop.common.constant.StringConstants.STATUS_CREATED;
import static com.github.NervousOrange.wxshop.common.constant.StringConstants.STATUS_DELETED;

@Service
public class ShopService {

    private final ShopMapper shopMapper;

    @Autowired
    public ShopService(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    public Shop createShop(Shop shop) {
        shop.setStatus(STATUS_CREATED);
        int id = shopMapper.insertSelective(shop);
        shop.setId(id);
        return shop;
    }

    public Shop updateShopById(Integer shopId, Shop shop) {
        shop.setId(shopId);
        shop.setUpdatedAt(new Date());
        shopMapper.updateByPrimaryKeySelective(shop);
        return shopMapper.selectByPrimaryKey(shopId);
    }

    public Shop deleteShopById(Integer shopId) {
        Shop shop = new Shop();
        shop.setId(shopId);
        shop.setStatus(STATUS_DELETED);
        shopMapper.updateByPrimaryKeySelective(shop);
        return shopMapper.selectByPrimaryKey(shopId);
    }

    public Shop getShopById(Integer shopId) {
        return shopMapper.selectByPrimaryKey(shopId);
    }

    public PagedResponse<List<Shop>> getShopList(Integer pageNum, Integer pageSize, User currentUser) {
        Integer userId = currentUser.getId();
        ShopExample example = new ShopExample();
        example.createCriteria().andOwnerUserIdEqualTo(userId).andStatusNotEqualTo(STATUS_DELETED);
        long shopCount = shopMapper.countByExample(example);
        Integer totalPage = Math.toIntExact(shopCount / pageSize);
        Integer offset = (pageNum - 1) * pageSize;
        Integer limit = pageSize;
        example.setOffset(offset);
        example.setLimit(limit);
        List<Shop> shops = shopMapper.selectByExample(example);
        return new PagedResponse<>(pageNum, pageSize, totalPage, shops);
    }
}
