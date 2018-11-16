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
 * 数据源
 */
public class GirlRepository {
    private static final String TAG = "relight";
    private static final String URL = "https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&" +
            "is=&fp=result&queryWord=%E5%8F%A4%E8%A3%85%E7%BE%8E%E5%A5%B3&cl=2&lm=-1&ie=utf-8&" +
            "oe=utf-8&adpicid=&st=-1&z=&ic=0&word=%E5%8F%A4%E8%A3%85%E7%BE%8E%E5%A5%B3&s=&se=&" +
            "tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&expermode=&cg=girl&pn=30&rn=30&" +
            "gsm=1e&1542348345179=";
    private static final GirlRepository INSTANCE = new GirlRepository();
    private UUID uuid;

    public static GirlRepository getInstance() {
        return INSTANCE;
    }

    @WorkerThread
    @Nullable
    public GirlResponseBean fetchData() {
        if (uuid != null) {
            Log.w(TAG, "cancel request " + uuid);
            OkGo.getInstance().cancelTag(uuid);
        }
        uuid = UUID.randomUUID();
        try {
            Log.w(TAG, "start request " + uuid);
            okhttp3.Response response = OkGo.<String>get(URL).tag(uuid).execute();
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
    public void fetchData(final Callback callback) {
        if (uuid != null) {
            Log.w(TAG, "cancel request " + uuid);
            OkGo.getInstance().cancelTag(uuid);
        }
        uuid = UUID.randomUUID();
        OkGo.<String>get(URL).tag(uuid).execute(new StringCallback() {
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
