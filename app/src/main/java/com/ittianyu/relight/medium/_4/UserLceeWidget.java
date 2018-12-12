package com.ittianyu.relight.medium._4;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;

import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.stateful.lcee.CommonEmptyWidget;
import com.ittianyu.relight.widget.stateful.lcee.CommonLoadingWidget;
import com.ittianyu.relight.widget.stateful.lcee.LceeWidget;
import com.ittianyu.relight.widget.stateful.lcee.LceeStatus;
import com.ittianyu.relight.widget.stateful.rm.RmStatus;

public class UserLceeWidget extends LceeWidget {
    private View.OnClickListener reload = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reload();
        }
    };
    private UserRmWidget contentWidget;

    public UserLceeWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
        // 为了在 onLoadData 中调用 contentWidget， 需要放在构造方法里执行，
        // 否则只有当加载完数据之后才会 调用 renderContent 进行初始化
        contentWidget = new UserRmWidget(context, lifecycle)
                .onReloadListener(reload)
                .showEmpty(new Runnable() {
                    @Override
                    public void run() {
                        showEmpty();
                    }
                });
    }

    @Override
    protected Widget renderLoading() {
        return new CommonLoadingWidget(context, lifecycle);
    }

    @Override
    protected Widget renderContent() {
        return contentWidget;
    }

    @Override
    protected Widget renderEmpty() {
        return new CommonEmptyWidget(context, lifecycle, "No data. Click to reload", reload);
    }

    @Override
    protected Widget renderError() {
        lastError.printStackTrace();
        return new CommonEmptyWidget(context, lifecycle, "Network error. Click to reload", reload);
    }

    @Override
    protected LceeStatus onLoadData() throws Exception {
        RmStatus status = contentWidget.onLoadData();
        if (status == RmStatus.RefreshContent) {
            contentWidget.showRefreshContent();
        }
        return status.toLceeStatus();
    }

}
