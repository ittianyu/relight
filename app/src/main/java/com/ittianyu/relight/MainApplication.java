package com.ittianyu.relight;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.model.HttpHeaders;
import com.squareup.leakcanary.LeakCanary;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MainApplication extends Application {
    private static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        initHttpClient();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MainApplication getInstance() {
        return instance;
    }

    private void initHttpClient() {
        //参阅 https://github.com/jeasonlzy/okhttp-OkGo/wiki/Init#%E5%85%A8%E5%B1%80%E9%85%8D%E7%BD%AE
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .readTimeout(8, TimeUnit.SECONDS)
                .writeTimeout(8, TimeUnit.SECONDS)
                .followRedirects(false)
                .followSslRedirects(true)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cookieJar(new CookieJarImpl(new SPCookieStore(instance)))
                .build();
        OkGo.getInstance().init(instance)
                .setOkHttpClient(okHttpClient)
                .setRetryCount(1)
                .setCacheMode(CacheMode.DEFAULT)
                .addCommonHeaders(new HttpHeaders("Charset", "UTF-8"));
    }

}
