package com.blockadm.common.http;

import com.blockadm.common.BuildConfig;
import com.blockadm.common.utils.ConstantUtils;
import com.blockadm.common.utils.ContextUtils;
import com.blockadm.common.utils.L;
import com.blockadm.common.utils.SharedpfTools;
import com.blockadm.common.utils.SystemUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by hp on 2018/12/11.
 */

public class RetrofitManager {

    private static final int DEFAULT_TIME_OUT = 25;//超时时间5s
    private static final int DEFAULT_READ_TIME_OUT = 15;//读取时间
    private static final int DEFAULT_WRITE_TIME_OUT = 15;//读取时间
    private Retrofit mRetrofit;


    private RetrofitManager(){
        //OkHttpClient配置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_WRITE_TIME_OUT,TimeUnit.SECONDS);
        addInterceptor(builder);
        if (BuildConfig.isDebug){
            mRetrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl(ApiService.BASR_URL_RELEASE)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }else{
            mRetrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl(ApiService.BASR_URL_RELEASE)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    /**
     * 添加各种拦截器
     * @param builder
     */
    private void addInterceptor(OkHttpClient.Builder builder) {
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                String token = (String) SharedpfTools
                        .getInstance(ContextUtils.getBaseApplication()).get(ConstantUtils.TOKEN,"");

//                L.d("token: "+token);

//                Request request1 = chain.request();
//                String body = request1.body().toString();
//                L.d("request body: "+body);

                Request request = chain.request()
                        .newBuilder()
                        // .addHeader(ConstantUtils.TOKEN, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJ1c2VyX2lkIjoiMTAiLCJpc3MiOiJTZXJ2aWNlIiwiZXhwIjoxNTUxMDc1NzUwLCJpYXQiOjE1NTAyMTE3NTB9.f3MMbYreC3rR6tXbdYuQ53pVtqarZedw_-b5VQ_bTN0")
                        .addHeader(ConstantUtils.TOKEN, token)
                        .addHeader("user-agent","android "+ SystemUtils.getSystemVersion())
                        .build();
                return chain.proceed(request);
            }
        });
        builder.addInterceptor(new Interceptor(){
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                HttpUrl url =  request.url();

//                Log.i("respInterceptor-url",url.toString());
                Response response;
                try {
                    response = chain.proceed(request);
                } catch (Exception e) {
                    L.all("respInterceptor-e",e.toString());
                    throw e;
                }
                ResponseBody responseBody = response.body();
                long contentLength = responseBody.contentLength();
                Headers headers = response.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
//                    Log.i("respInterceptor-headers",headers.name(i) + ": " + headers.value(i));
                }
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset UTF8 = Charset.forName("UTF-8");
                if (contentLength != 0) {
                    String  jsonData =  buffer.clone().readString(UTF8);
                    L.all("respInterceptor-data","url : "+url +" \n jsonData : "+jsonData);
                }

                return response;
            }


        });

    }
    private static class SingletonHolder{
        private static RetrofitManager retrofitServiceManager = new RetrofitManager();
    }

    public static RetrofitManager getInstance(){
        return SingletonHolder.retrofitServiceManager;
    }

    //获取Service实例
    public <T> T creat(Class<T> tClass){
        return mRetrofit.create(tClass);
    }

    public static ApiService getService(){
        return RetrofitManager.getInstance().creat(ApiService.class);
    }

}
