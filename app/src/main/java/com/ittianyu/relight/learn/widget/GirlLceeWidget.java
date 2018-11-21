package com.ittianyu.relight.learn.widget;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.view.View;

import com.ittianyu.relight.learn.bean.GirlItemBean;
import com.ittianyu.relight.learn.bean.GirlResponseBean;
import com.ittianyu.relight.learn.repository.GirlRepository;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.stateful.lcee.LceeWidget;
import com.ittianyu.relight.widget.stateful.lcee.Status;

import java.util.List;

public class GirlLceeWidget extends LceeWidget {
    private List<GirlItemBean> data;
    private int pageIndex = 1;
    private View.OnClickListener reloadListener;
    private String errorReason;

    public GirlLceeWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
        reloadListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload();
            }
        };
    }

    @Override
    protected Widget renderLoading() {
        return new GirlLoadingWidget(context, lifecycle, "加载中");
    }

    @Override
    protected Widget renderContent() {
        return new GirlListWidget(context, lifecycle, this);
    }

    @Override
    protected Widget renderEmpty() {
        return new GirlEmptyWidget(context, lifecycle, "暂无数据", reloadListener);
    }

    @Override
    protected Widget renderError() {
        return new GirlErrorWidget(context, lifecycle, "出错了: " + errorReason, reloadListener);
    }

    @Override
    protected Status onLoadData() {
        GirlResponseBean bean = GirlRepository.getInstance().fetchData(pageIndex);
        if (bean == null) {
            errorReason = "response bean is null";
            return Status.Error;
        }
        if (bean.isError()) {
            errorReason = bean.getErrorReason();
            return Status.Error;
        }
        data = bean.getResults();
        if (data == null || data.size() == 0) {
            return Status.Empty;
        }
        return Status.Content;
    }

    @Override
    public void initWidget(FrameWidget widget) {
        widget.matchParent();
//        GirlRepository.getInstance().fetchData(pageIndex, new GirlRepository.Callback() {
//            @Override
//            public void onSuccess(final GirlResponseBean responseBean) {
//                data = responseBean.getResults();
//                showContent();
//            }
//
//            @Override
//            public void onFailure() {
//                showError();
//            }
//        });
    }

    public List<GirlItemBean> getData() {
        return data;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void onRefresh() {
        pageIndex = 1;
        reload();
    }

    public void onLoadMore() {
        pageIndex++;
        reload();
    }

}
