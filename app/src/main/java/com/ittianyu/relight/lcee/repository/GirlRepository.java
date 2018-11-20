package com.ittianyu.relight.lcee.repository;

import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.ittianyu.relight.lcee.bean.GirlResponseBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.IOException;
import java.util.UUID;

import okhttp3.ResponseBody;

/**
 * 数据仓库
 */
public class GirlRepository {
    private static final String TAG = "relight";
    private static final String URL = "https://gank.io/api/data/%E7%A6%8F%E5%88%A9/{pageSize}/{pageIndex}";
    private static final GirlRepository INSTANCE = new GirlRepository();
    private UUID uuid;

    public static GirlRepository getInstance() {
        return INSTANCE;
    }

    private String wrapUrl(int pageIndex) {
        return URL.replace("{pageSize}", "10")
                .replace("{pageIndex}", String.valueOf(pageIndex));
    }

    @WorkerThread
    @Nullable
    public GirlResponseBean fetchData(int pageIndex) {
        if (uuid != null) {
            Log.w(TAG, "cancel request " + uuid);
            OkGo.getInstance().cancelTag(uuid);
        }
        uuid = UUID.randomUUID();
        try {
            Log.w(TAG, "start request " + uuid);
            okhttp3.Response response = OkGo.<String>get(wrapUrl(pageIndex)).tag(uuid).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                if (body == null) {
                    Log.w(TAG, "request failure: " + uuid);
                    uuid = null;
                    return null;
                }
                String json = body.string();
                Log.w(TAG, "request success: " + json);
                return JSON.parseObject(json, GirlResponseBean.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.w(TAG, "request failure: " + uuid);
        uuid = null;
        return null;
    }

    @MainThread
    public void fetchData(int pageIndex,final Callback callback) {
        if (uuid != null) {
            Log.w(TAG, "cancel request " + uuid);
            OkGo.getInstance().cancelTag(uuid);
        }
        uuid = UUID.randomUUID();
        OkGo.<String>get(wrapUrl(pageIndex)).tag(uuid).execute(new StringCallback() {
            @Override
            public void onStart(Request<String, ? extends Request> request) {
                Log.w(TAG, "start request " + uuid);
                callback.onStart();
            }

            @Override
            public void onSuccess(Response<String> response) {
                Log.w(TAG, "request success: " + uuid + "," + response.message());
                uuid = null;
                GirlResponseBean bean = JSON.parseObject(response.body(), GirlResponseBean.class);
                callback.onSuccess(bean);
            }

            @Override
            public void onError(Response<String> response) {
                Log.w(TAG, "request failure: " + uuid);
                uuid = null;
                callback.onFailure();
            }
        });
    }

    @SuppressWarnings("WeakerAccess")
    public static abstract class Callback {
        public void onStart() {
        }

        public abstract void onSuccess(GirlResponseBean data);

        public void onFailure() {
        }
    }

}
