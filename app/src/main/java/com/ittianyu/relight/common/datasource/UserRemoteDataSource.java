package com.ittianyu.relight.common.datasource;

import com.alibaba.fastjson.JSON;
import com.ittianyu.relight.common.bean.UserRemoteBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class UserRemoteDataSource {
    private static UserRemoteDataSource instance = new UserRemoteDataSource();

    public static UserRemoteDataSource getInstance() {
        return instance;
    }

    public void fetchUser(final Callback callback) {
        OkGo.<String>get("https://easy-mock.com/mock/5bed446b93d15a3354f976e9/userinfo").execute(new StringCallback() {
            @Override
            public void onStart(Request<String, ? extends Request> request) {
                callback.onStart();
            }

            @Override
            public void onSuccess(Response<String> response) {
                UserRemoteBean bean = JSON.parseObject(response.body(), UserRemoteBean.class);
                callback.onSuccess(bean);
            }

            @Override
            public void onError(Response<String> response) {
                callback.onFailure();
            }
        });
    }

    public interface Callback {
        void onStart();

        void onSuccess(UserRemoteBean data);

        void onFailure();
    }

}
