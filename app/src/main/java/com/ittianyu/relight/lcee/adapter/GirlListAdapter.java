package com.ittianyu.relight.lcee.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ittianyu.relight.R;
import com.ittianyu.relight.lcee.bean.GirlItemBean;

import java.util.ArrayList;

/**
 * 类描述
 * <p>
 * Created by liyujiang on 2018/11/16 15:52
 */
public class GirlListAdapter extends BaseQuickAdapter<GirlItemBean, BaseViewHolder> {

    public GirlListAdapter() {
        super(R.layout.adapter_girl_list, new ArrayList<GirlItemBean>());
    }

    @Override
    protected void convert(BaseViewHolder helper, GirlItemBean item) {
        ImageView imageView = helper.getView(R.id.girl_item_thumb);
        Context context = imageView.getContext();
        Glide.with(context).load(item.getThumbURL()).into(imageView);
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        if (item.getWidth() > 0 && item.getHeight() > 0) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            int spanCount = 3;
            params.width = context.getResources().getDisplayMetrics().widthPixels / spanCount;
            params.height = (params.width * item.getHeight()) / item.getWidth();
        } else {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            params.width = 200;
            params.height = 200;
        }
        imageView.setLayoutParams(params);
    }

}
