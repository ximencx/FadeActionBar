package com.xm.fadebar.engine;

import com.xm.fadebar.entity.MyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据
 * 作者：ximencx on 2016/5/28 22:06
 * 邮箱：454366460@qq.com
 */
public class Data {

    public static List getData() {
        List mlist = new ArrayList<MyBean>();

        mlist.add(new MyBean("从明天起，做一个幸福的人 "));
        mlist.add(new MyBean("喂马、劈柴，周游世界 "));
        mlist.add(new MyBean("从明天起，关心粮食和蔬菜 "));
        mlist.add(new MyBean("我有一所房子，面朝大海，春暖花开 "));
        mlist.add(new MyBean("从明天起，和每一个亲人通信 "));
        mlist.add(new MyBean("告诉他们我的幸福 "));
        mlist.add(new MyBean("那幸福的闪电告诉我的 "));
        mlist.add(new MyBean("我将告诉每一个人 "));
        mlist.add(new MyBean("给每一条河每一座山取一个温暖的名字 "));
        mlist.add(new MyBean("陌生人，我也为你祝福  "));
        mlist.add(new MyBean("愿你有一个灿烂的前程  "));
        mlist.add(new MyBean("愿你有情人终成眷属  "));
        mlist.add(new MyBean("愿你在尘世获得幸福   "));
        mlist.add(new MyBean("我只愿面朝大海，春暖花开  "));

        return mlist;
    }
}
