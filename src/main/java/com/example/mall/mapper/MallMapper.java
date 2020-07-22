package com.example.mall.mapper;

import com.example.mall.entity.*;


import java.util.List;
import java.util.Map;

public interface MallMapper {
    List<Shops> queryshops();
    Shops queryshopbyid(int sid);
    void adduser(User user);
    User loginuser(Map map);
    void addcart(Cart card);
    List<Cart> querycartbyid(int uid);
    void delcartbyid(int cid);
    List<Clothes> queryclothes();
    List<Pants> querypants();
    List<Shoe> queryshoe();
    List<Lbt> querylbt();
}
