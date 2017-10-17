package com.konradkluz.websourceviewer.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import com.konradkluz.websourceviewer.ui.MainActivityComponent;
import com.konradkluz.websourceviewer.viewmodel.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by konradkluz on 17/10/2017.
 */

@Module(subcomponents = {
        MainActivityComponent.class
})
public class ApplicationModule {

    @Provides
    ViewModelProvider.Factory provideViewModelFactory(ViewModelFactory factory) {
        return factory;
    }

    @Provides
    @Singleton
    public Context provideContext(Application application) {
        return application;
    }
}
