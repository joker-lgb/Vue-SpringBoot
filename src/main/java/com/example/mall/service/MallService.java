package com.example.mall.service;

import com.example.mall.entity.Card;
import com.example.mall.entity.Shops;
import com.example.mall.entity.User;

import java.util.List;
import java.util.Map;

public interface MallService {

    List<Shops> queryshops();
    Shops queryshopbyid(int sid);
    void adduser(User user);
    User loginuser(Map map);
    Map<String,Object> lockUser(String name);
    String loginValdate(String name);
    void addcard(Card card);
    List<Card> querycardbyid(int uid);
    void delkey(String name);
    void delcradbyid(int cid);
}
