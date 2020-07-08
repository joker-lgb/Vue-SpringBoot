package com.example.mall.service.impl;

import com.example.mall.entity.Card;
import com.example.mall.entity.Shops;
import com.example.mall.entity.User;
import com.example.mall.mapper.MallMapper;
import com.example.mall.service.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MallServiceimpl implements MallService {
    @Autowired
    RedisTemplate redisTemplate;

    @Resource(name = "redisTemplate1")
    private ValueOperations<String, String> string;

    @Resource
    private MallMapper mallMapper;
    @Override
    public List<Shops> queryshops() {
        return mallMapper.queryshops();
    }

    @Override
    public Shops queryshopbyid(int sid) {
        return mallMapper.queryshopbyid(sid);
    }

    @Override
    public void adduser(User user) {
        mallMapper.adduser(user);
    }

    @Override
    public User loginuser(Map map) {
        return mallMapper.loginuser(map);
    }

    @Override
    public Map<String, Object> lockUser(String name) {
        String key="userlock"+name;
        Map<String,Object> map=new HashMap<>();
        if (redisTemplate.hasKey(key)){
            Long expire = redisTemplate.getExpire(key,TimeUnit.MINUTES);
            map.put("flag",true);
            map.put("locktime",expire);
        }else {
            map.put("flag",false);
        }
        return map;
    }

    @Override
    public String loginValdate(String name) {
        String key="userloginCount"+name;
        String key1="userlock"+name;
        String str="";
        int  count=5;
        if (!redisTemplate.hasKey(key)){
            string.set(key,"1");
            redisTemplate.expire(key, 2, TimeUnit.MINUTES);
            str="用户名或密码错误，两分钟内还能输入"+(count-1)+"次";
            return str;
        }else{
            Long s = Long.valueOf(string.get(key));
            if (s<count-1){
                string.increment(key,1);
                Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
                str="在"+expire+"秒内还能输入"+(count-1-s)+"次";
                return str;
            }else {
                string.set(key1,"1");
                redisTemplate.expire(key1,1,TimeUnit.HOURS);
                str="密码错误超过"+count+"次,限制登录1小时";
                return str;
            }
        }
    }
    @Override
    public void delkey(String name) {
          String key="userloginCount"+name;
          redisTemplate.delete(key);
    }

    @Override
    public void delcradbyid(int cid) {
        mallMapper.delcardbyid(cid);
    }

    @Override
    public void addcard(Card card) {
        mallMapper.addcard(card);
    }

    @Override
    public List<Card> querycardbyid(int uid) {
        return mallMapper.querycardbyid(uid);
    }


}
