package com.xm.fadebar.adapter;

import android.content.Context;
import android.widget.TextView;

import com.xm.fadebar.R;
import com.xm.fadebar.entity.MyBean;

import butterknife.ButterKnife;
import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * listiew适配器
 * 作者：ximencx on 2016/5/28 18:44
 * 邮箱：454366460@qq.com
 */
public class MyAdapter extends BGAAdapterViewAdapter<MyBean> {
    public MyAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, MyBean bean) {
        TextView tv_info = ButterKnife.findById(bgaViewHolderHelper.getConvertView(), R.id.tv_info);
        tv_info.setText(bean.getInfo());
    }
}
