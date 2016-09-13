package com.youtube.sample.app.dagger.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.youtube.sample.app.BuildConfig;
import com.youtube.sample.app.R;
import com.youtube.sample.app.net.service.ApiService;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jakewharton.byteunits.DecimalByteUnit.MEGABYTES;
import static java.util.concurrent.TimeUnit.SECONDS;

@Singleton
@Module //
public final class ApiModule {

    private static final int DISK_CACHE_SIZE = (int) MEGABYTES.toBytes(50);
    private static final long CONNECT_TIMEOUT = 60;
    private static final long READ_TIMEOUT = 60;
    private static final long WRITE_TIMEOUT = 60;

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);
        Cache cache = new Cache(new File(context.getCacheDir(), "http"), DISK_CACHE_SIZE);
        return new OkHttpClient.Builder().addInterceptor(interceptor)
                .cache(cache)
                .connectTimeout(CONNECT_TIMEOUT, SECONDS)
                .readTimeout(READ_TIMEOUT, SECONDS)
                .writeTimeout(WRITE_TIMEOUT, SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().setLongSerializationPolicy(LongSerializationPolicy.DEFAULT).setPrettyPrinting().create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Context context, OkHttpClient client, Gson gson) {
        return new Retrofit.Builder().baseUrl(context.getString(R.string.youtube_search_url))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
