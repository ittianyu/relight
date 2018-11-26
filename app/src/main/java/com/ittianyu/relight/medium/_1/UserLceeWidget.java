package com.ittianyu.relight.medium._1;

import android.accounts.NetworkErrorException;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;


import com.ittianyu.relight.common.adapter.UserItemAdapter;
import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.datasource.UserDataSource;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.BaseAndroidWidget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.native_.RecyclerWidget;
import com.ittianyu.relight.widget.stateful.lcee.CommonEmptyWidget;
import com.ittianyu.relight.widget.stateful.lcee.CommonLoadingWidget;
import com.ittianyu.relight.widget.stateful.lcee.LceeWidget;
import com.ittianyu.relight.widget.stateful.lcee.Status;

import java.util.*;

public class UserLceeWidget extends LceeWidget {
    private List<UserBean> data = Collections.emptyList();
    private View.OnClickListener reload = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reload();
        }
    };

    public UserLceeWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected Widget renderLoading() {
        return new CommonLoadingWidget(context, lifecycle);
    }

    @Override
    protected Widget renderContent() {
        return new FrameWidget(context, lifecycle,
                renderRecycler(),
                renderFab()
        ).matchParent();
    }

    @Override
    protected Widget renderEmpty() {
        return new CommonEmptyWidget(context, lifecycle, "No data. Click to reload", reload);
    }

    @Override
    protected Widget renderError() {
        return new CommonEmptyWidget(context, lifecycle, "Network error. Click to reload", reload);
    }

    @Override
    protected Status onLoadData() throws NetworkErrorException {
        data = UserDataSource.getInstance().getUsersFromRemote();
        if (data.isEmpty())
            return Status.Empty;
        return Status.Content;
    }

    private RecyclerWidget renderRecycler() {
        return new RecyclerWidget<UserItemAdapter>(context, lifecycle) {
            @Override
            protected void initProps() {
                width = matchParent;
                height = matchParent;
                layoutManager = new LinearLayoutManager(context);
                adapter = new UserItemAdapter(lifecycle);
            }

            @Override
            public void updateView(RecyclerView view) {
                adapter.setData(data);
            }
        };
    }

    private BaseAndroidWidget<FloatingActionButton> renderFab() {
        return new BaseAndroidWidget<FloatingActionButton>(context, lifecycle) {
            @Override
            protected void initProps() {
                layoutGravity = Gravity.END | Gravity.BOTTOM;
                margin = dp(16);
                wrapContent();
            }
        }.onClickListener(reload);
    }

}
