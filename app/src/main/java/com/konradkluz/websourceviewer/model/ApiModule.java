package com.konradkluz.websourceviewer.model;

import com.konradkluz.websourceviewer.model.api.PageSourceService;
import com.konradkluz.websourceviewer.model.repository.RemoteRepository;
import com.konradkluz.websourceviewer.model.repository.RemoteRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by konradkluz on 01/10/2017.
 */
@Module
public class ApiModule {

    @Provides
    public OkHttpClient provideClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    @Singleton
    public PageSourceService providePageSourceService() {
        return new Retrofit.Builder()
                .baseUrl("https://www.wp.pl")
                .client(provideClient())
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
