package com.example.mall.service;

import com.example.mall.entity.*;

import java.util.List;
import java.util.Map;

public interface MallService {

    List<Shops> queryshops();
    Shops queryshopbyid(int sid);
    void adduser(User user);
    User loginuser(Map map);
    Map<String,Object> lockUser(String name);
    String loginValdate(String name);
    void addcart(Cart cart);
    List<Cart> querycartbyid(int uid);
    void delkey(String name);
    void delcratbyid(int cid);
    List<Clothes> queryclothes();
    List<Pants> querypants();
    List<Shoe> queryshoe();
    List<Lbt> querylbt();

}
