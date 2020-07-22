package com.example.mall.controller;


import com.example.mall.entity.*;
import com.example.mall.service.MallService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public PageInfo queryshops(@RequestParam(value = "currentpage",defaultValue = "1") int current){
        PageHelper.startPage(current,4);
        PageInfo pageInfo=new PageInfo(mallService.queryshops());
        return pageInfo;
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
    @RequestMapping("cart")
    public boolean addcard(@RequestParam("sid") int sid,@RequestParam("uid") int uid,
                           @RequestParam("sname")  String  sname ,@RequestParam ("scount") String scount,
                           @RequestParam("totalprice") String totalprice,@RequestParam("simg") String simg){
        Cart cart=new Cart();
        cart.setSid(sid);
        cart.setUid(uid);
        cart.setSname(sname);
        cart.setScount(scount);
        cart.setTotalprice(Double.valueOf(totalprice));
        cart.setSimg(simg);
        mallService.addcart(cart);
        return true;
    }

    @ResponseBody
    @RequestMapping("querycart")
    public List<Cart> querycardbyid(@RequestParam(value = "uid",required = false) int uid){
        List<Cart> querycartbyid = mallService.querycartbyid(uid);
        return querycartbyid;
    }

    @ResponseBody
    @RequestMapping("delcartbyid")
    public boolean delcard(@RequestParam("cid") int cid){
        mallService.delcratbyid(cid);
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

    @ResponseBody
    @RequestMapping("queryclothes")
    public List<Clothes> query(){
        List<Clothes> clothes=mallService.queryclothes();
        return clothes;
    }

    @ResponseBody
    @RequestMapping("querypants")
    public List<Pants> querypants(){
        List<Pants> querypants = mallService.querypants();
        return querypants;
    }

    @ResponseBody
    @RequestMapping("queryshoe")
    public List<Shoe> queryshoe(){
        List<Shoe> queryshoe = mallService.queryshoe();
        return queryshoe;
    }

    @ResponseBody
    @RequestMapping("querylbt")
    public List<Lbt> querylbt(){
        List<Lbt> querylbt = mallService.querylbt();
        return querylbt;
    }
}
