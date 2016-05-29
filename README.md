============

开发者使用此依赖，只需要2个方法就可以实现美团外卖中上拉titlebar背景渐变，下拉titlebar隐藏效果


### 效果图
![Image of 下拉隐藏]()
![Image of 上拉渐变]()
![Image of 效果]()

## 使用前提
> 1.注意actionbar的依赖库，目前仅支持

```java
import android.app.ActionBar;
```
> 2.actionbar背景渐变需要监听headerview的位置，放在添加headerview后调用该方法

```java
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

```

> 3.注意activity对应的theme添加属性

```xml
<style name="AppTheme" parent="android:Theme.Holo.Light.DarkActionBar">
     <item name="android:windowActionBarOverlay">true</item>
     <item name="android:windowContentOverlay">@null</item>
</style>
```


### 基本使用

#### 1.添加Gradle依赖

```groovy
dependencies {
}
```

#### 2.activity中获取actionbar对象

```java
    private void initbar() {
		//获取actionbar对象
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

```

#### 3.listview的监听中调用helper.setActionBarAlpha()，注意减去状态栏的高度

```java
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
```

### sample中使用到的第三方库:

* [BGAAdapter](https://github.com/bingoogolapple/BGAAdapter-Android)
* [BGARefreshLayout-Android](https://github.com/bingoogolapple/BGARefreshLayout-Android)

### 关于我

<a  href="https://github.com/ximencx" target="_blank">github主页-ximencx</a> | <a href="mailto:454366460@gmail.com" target="_blank">个人邮箱：454366460@qq.com</a> 

## License

    Copyright 2015 bingoogolapple

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
