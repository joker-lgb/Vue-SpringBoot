package com.example.mall.controller;


import com.example.mall.entity.Card;
import com.example.mall.entity.Shops;
import com.example.mall.entity.User;
import com.example.mall.service.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@CrossOrigin
public class MallController {
    String code1;
    @Autowired
    private MallService mallService;

    @ResponseBody
    @RequestMapping("queryshops")
    public List<Shops> queryshops(){
        List<Shops> queryshops = mallService.queryshops();
        return queryshops;
    }

    @ResponseBody
    @RequestMapping("details")
    public Shops queryshopbuid(@RequestParam int sid){
      Shops shop=mallService.queryshopbyid(sid);
      return shop;
    }

    @ResponseBody
    @RequestMapping("adduser")
    public boolean add(User user){
        mallService.adduser(user);
        System.out.println(user);
        return true;
    }

    @ResponseBody
    @RequestMapping("login")
    public Object login(@RequestParam(value = "uname",required = false) String uname,@RequestParam(value = "password",required = false) String password,
                        @RequestParam("code") String code) {
        Map<String, Object> map1 = mallService.lockUser(uname);
        if ((boolean) map1.get("flag")) {
            String str = "登录失败，还剩" + (map1.get("locktime")) + "分钟";
            return str;
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("uname", uname);
            map.put("password", password);
            User user = mallService.loginuser(map);
            if (user != null) {
                mallService.delkey(uname);
                if (code.equalsIgnoreCase(code1)){
                    return user;
                }else {
                    String err="验证码错误";
                    return  err;
                }

            } else {
                String name = mallService.loginValdate(uname);
                return name;
            }
        }
    }

    @ResponseBody
    @RequestMapping("card")
    public boolean addcard(@RequestParam("sid") int sid,@RequestParam("uid") int uid,
                           @RequestParam("sname")  String  sname ,@RequestParam ("scount") String scount,
                           @RequestParam("totalprice") String totalprice,@RequestParam("simg") String simg){
        Card card=new Card();
        card.setSid(sid);
        card.setUid(uid);
        card.setSname(sname);
        card.setScount(scount);
        card.setTotalprice(Double.valueOf(totalprice));
        card.setSimg(simg);
        mallService.addcard(card);
        return true;
    }

    @ResponseBody
    @RequestMapping("querycard")
    public List<Card> querycardbyid(@RequestParam("uid") int uid){
        List<Card> querycardbyid = mallService.querycardbyid(uid);
        return querycardbyid;
    }

    @ResponseBody
    @RequestMapping("delcardbyid")
    public boolean delcard(@RequestParam("cid") int cid){
        mallService.delcradbyid(cid);
        return true;
    }

    public Color getcolor(){
        Random random=new Random();
        int r=  random.nextInt(256);
        int g=random.nextInt(256);
        int b=random.nextInt(256);
        return new Color(r,g,b);
    }

    public String getNum(){
        int num=(int)(Math.random()*9000)+1000;
        return String.valueOf(num);
    }

    @ResponseBody
    @RequestMapping(value = "code",produces = MediaType.IMAGE_JPEG_VALUE)//指定返回的类型为JPEG
    public byte[] img() throws IOException {
        BufferedImage bufferedImage=new BufferedImage(100,50,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.fillRect(0,0,100,50);
        for (int i = 0; i <60 ; i++) {
            Random random=new Random();
            int xBegin=random.nextInt(100);
            int yBegin=random.nextInt(50);
            int xEnd=random.nextInt(xBegin+10);
            int yEnd=random.nextInt(yBegin+10);
            graphics.drawLine(xBegin,yBegin,xEnd,yEnd);
            graphics.setColor(getcolor());

        }
        graphics.setColor(Color.BLACK);
        code1=getNum();
        graphics.setFont(new Font("seif",Font.BOLD,30));
        graphics.drawString(code1,30,40);
        //byte输出流
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage,"jpeg",os);//生成图片

        return os.toByteArray();
    }

}
