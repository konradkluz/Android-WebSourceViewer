package com.konradkluz.websourceviewer.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.konradkluz.websourceviewer.model.api.PageSourceService;
import com.konradkluz.websourceviewer.model.repository.RemoteRepository;
import com.konradkluz.websourceviewer.model.repository.RemoteRepositoryImpl;
import com.konradkluz.websourceviewer.util.Utils;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by konradkluz on 01/10/2017.
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    public OkHttpClient provideClient(Context context, Utils utils) {

        Cache cache = createCache(context);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(chain -> {
                    Response originalResponse = chain.proceed(chain.request());
                    if (utils.isOnline(context)) {
                        int maxAge = 60;
                        return originalResponse.newBuilder()
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .build();
                    } else {
                        int maxStale = 60 * 60 * 24 * 28;
                        return originalResponse.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .build();
                    }
                })
                .cache(cache)
                .build();
    }

    @NonNull
    private Cache createCache(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(httpCacheDirectory, cacheSize);
    }

    @Provides
    @Singleton
    public PageSourceService providePageSourceService(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("https://www.wp.pl")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(PageSourceService.class);
    }

    @Provides
    @Singleton
    RemoteRepository provideRemoteRepository(RemoteRepositoryImpl repository) {
        return repository;
    }

}
