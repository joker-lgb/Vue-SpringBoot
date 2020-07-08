package com.example.mall.mapper;

import com.example.mall.entity.Card;
import com.example.mall.entity.Shops;
import com.example.mall.entity.User;


import java.util.List;
import java.util.Map;

public interface MallMapper {
    List<Shops> queryshops();
    Shops queryshopbyid(int sid);
    void adduser(User user);
    User loginuser(Map map);
    void addcard(Card card);
    List<Card> querycardbyid(int uid);
    void delcardbyid(int cid);
}
