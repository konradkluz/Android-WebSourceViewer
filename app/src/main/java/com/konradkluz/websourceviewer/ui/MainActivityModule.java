package com.konradkluz.websourceviewer.ui;

import android.arch.lifecycle.MutableLiveData;

import com.konradkluz.websourceviewer.model.entities.Response;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by konradkluz on 17/10/2017.
 */
@Module
public class MainActivityModule {

    @Named("responseLiveData")
    @Provides
    @MainActivityScope
    MutableLiveData<Response<String>> provideMutableLiveDataResponse(){
        return new MutableLiveData<>();
    }

    @Named("loadingStatusLiveData")
    @Provides
    @MainActivityScope
    MutableLiveData<Boolean> provideLoadingStatusLiveData() {
        return new MutableLiveData<>();
    }

}
