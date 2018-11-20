package com.ittianyu.relight.lcee.widget;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;

import com.ittianyu.relight.lcee.bean.GirlItemBean;
import com.ittianyu.relight.lcee.bean.GirlResponseBean;
import com.ittianyu.relight.lcee.repository.GirlRepository;
import com.ittianyu.relight.widget.Widget;
import com.ittianyu.relight.widget.native_.FrameWidget;
import com.ittianyu.relight.widget.stateful.lcee.CommonEmptyWidget;
import com.ittianyu.relight.widget.stateful.lcee.CommonLoadingWidget;
import com.ittianyu.relight.widget.stateful.lcee.LceeWidget;
import com.ittianyu.relight.widget.stateful.lcee.Status;

import java.util.List;

public class GirlListLceeWidget extends LceeWidget {
    private List<GirlItemBean> data;
    private int pageIndex = 1;

    public GirlListLceeWidget(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    protected Widget renderLoading() {
        return new CommonLoadingWidget(context, lifecycle);
    }

    @Override
    protected Widget renderContent() {
        return new GirlListWidget(context, lifecycle, this);
    }

    @Override
    protected Widget renderEmpty() {
        return new CommonEmptyWidget(context, lifecycle, "暂无数据");
    }

    @Override
    protected Widget renderError() {
        return new CommonEmptyWidget(context, lifecycle, "出错了");
    }

    @Override
    protected Status onLoadData() {
        GirlResponseBean bean = GirlRepository.getInstance().fetchData(pageIndex);
        if (bean == null) {
            return Status.Error;
        }
        data = bean.getResults();
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
