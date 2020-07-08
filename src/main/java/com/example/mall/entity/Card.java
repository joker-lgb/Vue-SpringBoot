package com.example.mall.entity;

public class Card {
    private int cid;
    private int sid;
    private int uid;
    private String sname;
    private String scount;
    private Double totalprice;
    private String simg;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getScount() {
        return scount;
    }

    public void setScount(String scount) {
        this.scount = scount;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public String getSimg() {
        return simg;
    }

    public void setSimg(String simg) {
        this.simg = simg;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cid=" + cid +
                ", sid=" + sid +
                ", uid=" + uid +
                ", sname='" + sname + '\'' +
                ", scount='" + scount + '\'' +
                ", totalprice=" + totalprice +
                ", simg='" + simg + '\'' +
                '}';
    }
}
