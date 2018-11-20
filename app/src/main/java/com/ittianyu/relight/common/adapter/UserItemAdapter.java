package com.ittianyu.relight.common.adapter;

import android.arch.lifecycle.Lifecycle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ittianyu.relight.common.bean.UserBean;
import com.ittianyu.relight.common.widget.UserItemWidget;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 86839 on 2017/10/18.
 */

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.ProjectViewHolder> {
    private List<UserBean> data = new ArrayList<>();
    private Lifecycle lifecycle;

    public UserItemAdapter(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserItemWidget widget = new UserItemWidget(parent.getContext(), lifecycle);
        return new ProjectViewHolder(widget);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        UserBean item = getData().get(position);
        holder.widget.setData(item);

        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                itemClickListener.onClick(v, position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<UserBean> data) {
        if (null != data)
            this.data = data;
        else
            this.data.clear();

        notifyDataSetChanged();
    }

    public void addData(List<UserBean> data) {
        if (null == data || data.size() == 0)
            return;

        int index = getItemCount();
        this.data.addAll(data);
        notifyItemRangeInserted(index, data.size());
    }


    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        UserItemWidget widget;

        public ProjectViewHolder(UserItemWidget widget) {
            super(widget.render());
            this.widget = widget;
        }
    }

    public List<UserBean> getData() {
        return data;
    }

    private ItemClickListener itemClickListener;

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static interface ItemClickListener {
        void onClick(View view, int position);
    }
}
