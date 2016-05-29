package com.xm.library.view;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * 修改背景
 * 作者：ximencx on 2016/5/28 18:25
 * 邮箱：454366460@qq.com
 */
public abstract class XMFadeBarHelper {

    private static final String TAG = "XMFadeBarHelper";

    private static final int MAX_ALPHA = 255;

    private int defaultAlpha;

    private Drawable mDrawable;

    private ActionBar mActionBar;

    /**
     * 构造方法 不能为null
     *
     * @param actionBar    actionbar对象
     * @param drawable     actionbar背景
     * @param defaultAlpha 默认背景初始值 如果显示为255 不显示0
     */
    public XMFadeBarHelper(final ActionBar actionBar, final Drawable drawable, final int defaultAlpha) {
        mActionBar = actionBar;
        this.defaultAlpha = defaultAlpha;
        setActionBarBackgroundDrawable(drawable);
    }

    /**
     * 设置背景
     *
     * @param drawable 背景对象
     */
    private void setActionBarBackgroundDrawable(Drawable drawable) {
        setActionBarBackgroundDrawable(drawable, true);
    }

    /**
     * 设置背景
     * 重载
     *
     * @param drawable 背景对象
     * @param mutate   是否mutate
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setActionBarBackgroundDrawable(Drawable drawable, boolean mutate) {
        mDrawable = mutate ? drawable.mutate() : drawable;
        mActionBar.setBackgroundDrawable(mDrawable);
        if (defaultAlpha == MAX_ALPHA) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                defaultAlpha = mDrawable.getAlpha();
        } else {
            setActionBarAlpha(defaultAlpha);
        }
    }

    /**
     * 设置透明度
     *
     * @param alpha
     */
    public void setActionBarAlpha(int alpha) {
        //
        if (mDrawable == null) {
            return;
        }
        //
        View view = mActionBar.getCustomView();
        if (view != null) {
            //设置actionbar透明背景
            if (alpha >= -MAX_ALPHA && alpha < 0) {
                mDrawable.setAlpha(Math.abs(alpha));
            } else if (alpha < -MAX_ALPHA) {
                mDrawable.setAlpha(MAX_ALPHA);
            } else if (alpha >= 0) {
                mDrawable.setAlpha(0);
            }
            //设置actionbar内控件透明
            int scaleAlpha = alpha * setHeight(view) / MAX_ALPHA;
            if (alpha <= 0) {
                setViewAlpha(view, MAX_ALPHA);
            } else if (alpha > 0 && alpha < setHeight(view)) {
                setViewAlpha(view, (MAX_ALPHA - scaleAlpha));
            } else if (alpha > setHeight(view)) {
                setViewAlpha(view, 0);
            }
        }
    }

    /**
     * 批量设置控件内view的alpha值
     *
     * @param customView  actionbar布局对象
     * @param alpha 回调的alpha
     */
    protected abstract void setViewAlpha(View customView, int alpha);

    /**
     * 返回actionbar需要的缩放高度
     *
     * @param customView
     * @return
     */
    protected abstract int setHeight(View customView);

}
