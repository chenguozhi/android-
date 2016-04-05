package com.example.chen.adapter.activity;

/**
 * Created by fulin on 2016/4/5 0005.
 */
public class Info extends Object{
    private String text="";
    private int num=0;

    public Info(String t,int num) {
        this.text=t;
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
