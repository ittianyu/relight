package com.ittianyu.relight.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ittianyu.relight.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 86839 on 2017/10/18.
 */

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {
    private List<ProjectItem> data = new ArrayList<>();

    public ProjectsAdapter() {
    }

    public ProjectsAdapter(List<ProjectItem> data) {
        this.data = data;
    }

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        ProjectItem item = getData().get(position);

        holder.tvName.setText(item.getFull_name());
        holder.tvStar.setText(item.getStargazers_count() + " star");
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

    public void setData(List<ProjectItem> data) {
        if (null != data)
            this.data = data;
        else
            this.data.clear();

        notifyDataSetChanged();
    }

    public void addData(List<ProjectItem> data) {
        if (null == data || data.size() == 0)
            return;

        int index = getItemCount();
        this.data.addAll(data);
        notifyItemRangeInserted(index, data.size());
    }


    public static class ProjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvStar;

        public ProjectViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvStar = itemView.findViewById(R.id.tv_star);
        }
    }

    public List<ProjectItem> getData() {
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
