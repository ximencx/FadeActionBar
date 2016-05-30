package com.xm.fadebar.ui;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;
import com.xm.fadebar.R;
import com.xm.fadebar.adapter.MyAdapter;
import com.xm.fadebar.engine.Data;
import com.xm.fadebar.entity.MyBean;
import com.xm.library.view.XMFadeBarHelper;
import com.xm.library.view.XMSettings;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;


/**
 * activity例子
 * 需要注意的是必须监听header位置，此版本的actionbar对应的主题
 * 作者：ximencx on 2016/5/28 18:25
 * 邮箱：454366460@qq.com
 */
public class MainActivity extends Activity implements BGARefreshLayout.BGARefreshLayoutDelegate {


    /**
     * listview
     */
    @InjectView(R.id.lv_my)
    ListView mlistview;
    /**
     * 刷新框架
     */
    @InjectView(R.id.rl_refresh)
    BGARefreshLayout mRefreshLayout;

    /**
     * 上下文
     */
    private Context mContext;
    private List mlist;
    /**
     * actionbar对象
     */
    private ActionBar mActionBar;

    /**
     * 辅助类
     */
    private XMFadeBarHelper helper;


    /**
     * headerview
     */
    private View llheaderview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mContext = this;
        //
        initbar();
        initfresh();
        initData();
        initlistview();


    }

    /**
     * 初始化actionbar
     */
    private void initbar() {
        mActionBar = getActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.ab_title);

        /**
         * actionbar辅助类
         * parameter1：action对象
         * parameter2：acitonbar背景
         * parameter3：初始透明度
         */
        helper = new XMFadeBarHelper(mActionBar, getResources().getDrawable(R.drawable.bg_actionbar), 0) {
            /**
             * 设置需要隐藏view的透明度
             * 注意：是否设置background的区别
             *
             * @param customView  actionbar布局对象
             * @param alpha 回调的alpha
             */
            @Override
            public void setViewAlpha(View customView, int alpha) {
                ButterKnife.findById(customView, R.id.tv_info).setAlpha(alpha);
                ButterKnife.findById(customView, R.id.rl_bg).getBackground().setAlpha(alpha);
            }

            /**
             * 设置隐藏速度
             * 默认返回actionbar布局的高度，当然也可以以其它view为参照物
             * @param customView actionbar布局
             * @return
             */
            @Override
            public int setHeight(View customView) {
                return customView.getHeight();
            }
        };

    }

    /**
     * Listview滑动监听
     */
    private void initScroll() {
        //设置动态改变
        mlistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // [0]代表x坐标,location [1] 代表y坐标。
                int[] location = new int[2];
                // 实时设置actionbar透明度，监听header位置（必须是移除屏幕会产生负数的view）
                llheaderview.getLocationInWindow(location);
                helper.setActionBarAlpha(location[1] - XMSettings.getStatusBarHeight(mContext));
                Log.i("tag", "onScroll: " + (location[1] - XMSettings.getStatusBarHeight(mContext)));
            }
        });
    }

    /**
     * 初始化刷新
     */
    private void initfresh() {
        mRefreshLayout.setDelegate(this);
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(this, false);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_pull_down);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);
        mRefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mlist = new ArrayList<MyBean>();
        mlist.addAll(Data.getData());
        //mlist.addAll(Data.getData());
    }

    /**
     * 初始化listview
     */
    private void initlistview() {
        MyAdapter mAdapter = new MyAdapter(this, R.layout.lv_item_home);
        mAdapter.setDatas(mlist);
        mlistview.addHeaderView(initheadview());
        mlistview.setAdapter(mAdapter);
        //
        initScroll();
    }

    /**
     * 初始化headerview
     *
     * @return
     */
    private View initheadview() {
        llheaderview = LayoutInflater.from(mContext).inflate(R.layout.lv_header_home, null);
        ImageView ivheader = ButterKnife.findById(llheaderview, R.id.iv_header);
        Picasso.with(mContext)
                .load(R.drawable.banner_bg)
                .fit()
                .into(ivheader);
        return llheaderview;
    }


    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mRefreshLayout.endRefreshing();
        return;
    }

    /**
     * 上拉加载 已关闭
     *
     * @param refreshLayout
     * @return
     */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
