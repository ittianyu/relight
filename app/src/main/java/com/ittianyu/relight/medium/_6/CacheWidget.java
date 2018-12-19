package com.ittianyu.relight.medium._6;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.datasource.UserDataSource;
import com.ittianyu.relight.utils.StateUtils;
import com.ittianyu.relight.widget.native_.ButtonWidget;
import com.ittianyu.relight.widget.native_.LinearWidget;
import com.ittianyu.relight.widget.native_.TextWidget;
import com.ittianyu.relight.widget.stateful.LifecycleStatefulWidget;
import com.ittianyu.relight.widget.stateful.state.State;
import com.ittianyu.relight.widget.stateful.state.strategy.CacheIfTaskErrorStrategy;

public class CacheWidget extends LifecycleStatefulWidget<LinearLayout, LinearWidget> implements View.OnClickListener {
    private static final int btnNormal1 = 1;
    private static final int btnCacheError1 = 2;
    private static final int btnNetworkError1 = 3;
    private static final int btnBothError1 = 4;
    private static final int btnNormal2 = 5;
    private static final int btnCacheError2 = 6;
    private static final int btnNetworkError2 = 7;
    private static final int btnBothError2 = 8;

    private TextWidget tvResult;

    private UserBean user;

    private Runnable networkTask = new Runnable() {
        @Override
        public void run() {
            System.out.println("running network task...");
            user = UserDataSource.getInstance().getUserFromRemote();
            user.setName(user.getName() + "\tfrom network");
            System.out.println(user.getName());
        }
    };

    private Runnable cacheTask = new Runnable() {
        @Override
        public void run() {
            System.out.println("running cache task...");
            user = UserDataSource.getInstance().getUser();
            user.setName(user.getName() + "\tfrom cache");
            System.out.println(user.getName());
        }
    };

    private Runnable networkErrorTask = new Runnable() {
        @Override
        public void run() {
            System.out.println("running network task... error!");
            user = null;
            throw new RuntimeException("network error");
        }
    };
    private Runnable cacheErrorTask = new Runnable() {
        @Override
        public void run() {
            System.out.println("running cache task... error!");
            user = null;
            throw new RuntimeException("cache error");
        }
    };

    public CacheWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected State<LinearWidget> createState(Context context) {
        tvResult = new TextWidget(context, lifecycle);
        LinearWidget widget = new LinearWidget(context, lifecycle,
                new TextWidget(context, lifecycle).text("CacheThenTaskStrategy").marginTop(16.0f),
                new ButtonWidget(context, lifecycle).allCaps(false).text("normal").id(btnNormal1).onClickListener(this).widthMatchAndHeightWrap(),
                new ButtonWidget(context, lifecycle).allCaps(false).text("cache error").id(btnCacheError1).onClickListener(this).widthMatchAndHeightWrap(),
                new ButtonWidget(context, lifecycle).allCaps(false).text("network error").id(btnNetworkError1).onClickListener(this).widthMatchAndHeightWrap(),
                new ButtonWidget(context, lifecycle).allCaps(false).text("both error").id(btnBothError1).onClickListener(this).widthMatchAndHeightWrap(),

                new TextWidget(context, lifecycle).text("CacheIfTaskErrorStrategy").marginTop(16.0f),
                new ButtonWidget(context, lifecycle).allCaps(false).text("normal").id(btnNormal2).onClickListener(this).widthMatchAndHeightWrap(),
                new ButtonWidget(context, lifecycle).allCaps(false).text("cache error").id(btnCacheError2).onClickListener(this).widthMatchAndHeightWrap(),
                new ButtonWidget(context, lifecycle).allCaps(false).text("network error").id(btnNetworkError2).onClickListener(this).widthMatchAndHeightWrap(),
                new ButtonWidget(context, lifecycle).allCaps(false).text("both error").id(btnBothError2).onClickListener(this).widthMatchAndHeightWrap(),

                tvResult.textColor(Color.BLACK).marginTop(16.0f)
        );
        widget.orientation(LinearWidget.vertical)
                .matchParent()
                .padding(16.0f);
        return StateUtils.create(widget);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case btnNormal1:
                setStateAsyncWithCache(cacheTask, networkTask);
                break;
            case btnCacheError1:
                setStateAsyncWithCache(cacheErrorTask, networkTask);
                break;
            case btnNetworkError1:
                setStateAsyncWithCache(cacheTask, networkErrorTask);
                break;
            case btnBothError1:
                setStateAsyncWithCache(cacheErrorTask, networkErrorTask);
                break;
            case btnNormal2:
                setStateAsyncWithCache(new CacheIfTaskErrorStrategy(), cacheTask, networkTask);
                break;
            case btnCacheError2:
                setStateAsyncWithCache(new CacheIfTaskErrorStrategy(), cacheErrorTask, networkTask);
                break;
            case btnNetworkError2:
                setStateAsyncWithCache(new CacheIfTaskErrorStrategy(), cacheTask, networkErrorTask);
                break;
            case btnBothError2:
                setStateAsyncWithCache(new CacheIfTaskErrorStrategy(), cacheErrorTask, networkErrorTask);
                break;
        }
    }

    private String getUsername() {
        if (user == null) {
            return "no data";
        }
        return user.getName();
    }

    @Override
    public void updateWidget(LinearWidget widget) {
        System.out.println("updateWidget: " + getUsername());
        tvResult.text("result: " + getUsername());
    }
}
