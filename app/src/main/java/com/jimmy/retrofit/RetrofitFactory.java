package com.jimmy.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.support.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jimmy.project.BuildConfig;
import com.jimmy.project.Constants;
import com.jimmy.project.SessionManager;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    private static Retrofit instance;
    private static Gson converter;

    public static Retrofit createRetrofit() {
        return createRetrofit(createConverter());
    }

    public static Retrofit getInstance() {
        if (instance != null) {
            return instance;
        }

        return (instance = createRetrofit());
    }

    public static Retrofit createRetrofit(Gson gson) {
        if (gson == null) return null;

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        // Request interceptor for authorized requests
        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request agentRequest = originalRequest.newBuilder().header("User-Agent", "Jimmy/Android").build();

                final String sessionToken = SessionManager.getInstance().getToken();

                if (sessionToken != null && !sessionToken.isEmpty()) {
                    // If JWT token is not null, then we will append this as a header
                    String authHeader = String.format("%s %s", "JWT", sessionToken);
                    Request authorizedRequest = agentRequest.newBuilder()
                            .header("Authorization", authHeader)
                            .build();
                    return chain.proceed(authorizedRequest);
                } else {
                    // If JWT token is null, then we will proceed with original request
                    return chain.proceed(agentRequest);
                }
            }
        });

        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        builder.addInterceptor(interceptor);

        String apiBaseUrl = Constants.BASE_URL;

        Retrofit.Builder bd = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(apiBaseUrl)
                .client(builder.build());

        return (instance = bd.build());
    }

    @NonNull
    private static Gson createConverter() {
        converter = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .serializeNulls()
                .create();
        return converter;
    }
}
