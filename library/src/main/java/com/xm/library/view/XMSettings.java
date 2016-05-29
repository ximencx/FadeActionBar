package com.xm.library.view;

import android.app.ActionBar;
import android.content.Context;

import java.lang.reflect.Field;

/**
 * actinbar tools
 * user：ximencx on 2016/5/28 18:25
 * email:454366460@qq.com
 */
public class XMSettings {

    /**
     * disable actionbar animotion
     * before your show orhide
     *
     * @param actionBar actionbar object
     */
    public static void disableABCShowHideAnimation(ActionBar actionBar) {
        try {
            actionBar.getClass().getDeclaredMethod("setShowHideAnimationEnabled", boolean.class).invoke(actionBar, false);
        } catch (Exception exception) {
            try {
                Field mActionBarField = actionBar.getClass().getSuperclass().getDeclaredField("mActionBar");
                mActionBarField.setAccessible(true);
                Object icsActionBar = mActionBarField.get(actionBar);
                Field mShowHideAnimationEnabledField = icsActionBar.getClass().getDeclaredField("mShowHideAnimationEnabled");
                mShowHideAnimationEnabledField.setAccessible(true);
                mShowHideAnimationEnabledField.set(icsActionBar, false);
                Field mCurrentShowAnimField = icsActionBar.getClass().getDeclaredField("mCurrentShowAnim");
                mCurrentShowAnimField.setAccessible(true);
                mCurrentShowAnimField.set(icsActionBar, null);
                //icsActionBar.getClass().getDeclaredMethod("setShowHideAnimationEnabled", boolean.class).invoke(icsActionBar, false);
            } catch (Exception e) {
                //....
            }
        }
    }


    /**
     * get system statue height
     *
     * @param context context
     * @return statue height
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
