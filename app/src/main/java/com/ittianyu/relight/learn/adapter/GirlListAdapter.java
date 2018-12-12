package com.ittianyu.relight.learn.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ittianyu.relight.R;
import com.ittianyu.relight.learn.bean.GirlItemBean;

import java.util.ArrayList;

/**
 * 列表数据适配器
 * <p>
 * Created by liyujiang on 2018/11/16 15:52
 */
public class GirlListAdapter extends BaseQuickAdapter<GirlItemBean, BaseViewHolder> {

    public GirlListAdapter() {
        super(R.layout.adapter_girl_list, new ArrayList<GirlItemBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, GirlItemBean item) {
        final ImageView imageView = helper.getView(R.id.girl_item_thumb);
        Context context = imageView.getContext();
        final ViewGroup.LayoutParams params = imageView.getLayoutParams();
        int spanCount = 3;
        params.width = context.getResources().getDisplayMetrics().widthPixels / spanCount;
        Glide.with(context).applyDefaultRequestOptions(RequestOptions
                .placeholderOf(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)).load(item.getUrl()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                params.height = params.width * 3 / 2;
                imageView.setLayoutParams(params);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                params.height = (params.width * resource.getIntrinsicHeight()) / resource.getIntrinsicWidth();
                imageView.setLayoutParams(params);
                return false;
            }
        }).into(imageView);
    }

}
