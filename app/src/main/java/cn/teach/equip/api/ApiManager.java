package cn.teach.equip.api;

import android.util.Log;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者 by wuliang 时间 16/11/24.
 * <p>
 * 所有的请求控制
 */

public class ApiManager {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static final String TAG = "ApiManager";
    private Retrofit mRetrofit;
    private static final int DEFAULT_TIMEOUT = 60;
    private OkHttpClient.Builder builder;

    /**
     * 初始化请求体
     */
    private ApiManager() {
        //手动创建一个OkHttpClient并设置超时时间
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.i(TAG, "log: " + message));
        loggingInterceptor.setLevel(level);
        builder.addInterceptor(loggingInterceptor);
    }

    private static class SingletonHolder {
        private static final ApiManager INSTANCE = new ApiManager();
    }

    //获取单例
    public static ApiManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取请求代理
     *
     * @param service
     * @param url
     * @param <T>
     * @return
     */
    public <T> T configRetrofit(Class<T> service, String url) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return mRetrofit.create(service);
    }


    /**
     * 获取STS服务的代理服务
     */
    public <T> T configRetrofit2(Class<T> service, String url) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return mRetrofit.create(service);
    }




    /**
     * 所有请求头统一处理
     */
//    private Interceptor headerInterceptor = chain -> {
//        Request request;
//        if (!StringUtils.isEmpty(App.token)) {
//            // 以拦截到的请求为基础创建一个新的请求对象，然后插入Header
//            request = chain.request().newBuilder()
//                    .addHeader("Authorization", App.token)
//                    .build();
//            return chain.proceed(request);
//        }
//        return chain.proceed(chain.request());
//    };

}