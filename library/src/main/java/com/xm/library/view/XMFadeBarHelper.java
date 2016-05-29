package com.xm.library.view;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * motify bg
 * user：ximencx on 2016/5/28 18:25
 * email:454366460@qq.com
 */
public abstract class XMFadeBarHelper {

    private static final String TAG = "XMFadeBarHelper";

    private static final int MAX_ALPHA = 255;

    private int defaultAlpha;

    private Drawable mDrawable;

    private ActionBar mActionBar;

    /**
     * custruct method (not null)
     *
     * @param actionBar    actionbar object
     * @param drawable     actionbar bg
     * @param defaultAlpha first value, if show,set value 255,then hide set value 0;
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
     * set alpha
     *
     * @param alpha setalpha
     */
    public void setActionBarAlpha(int alpha) {
        //
        if (mDrawable == null) {
            return;
        }
        //
        View view = mActionBar.getCustomView();
        if (view != null) {
            //set actionbar bg alpha
            if (alpha >= -MAX_ALPHA && alpha < 0) {
                mDrawable.setAlpha(Math.abs(alpha));
            } else if (alpha < -MAX_ALPHA) {
                mDrawable.setAlpha(MAX_ALPHA);
            } else if (alpha >= 0) {
                mDrawable.setAlpha(0);
            }
            //set actionbar view alpha
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
     * set actionbar view alpha
     *
     * @param customView  actionbar customview
     * @param alpha the alpha of your need
     */
    protected abstract void setViewAlpha(View customView, int alpha);

    /**
     * set actionbar speed
     *
     * @param customView actionbar custmview
     * @return the reference of height
     */
    protected abstract int setHeight(View customView);

}
