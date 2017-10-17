package com.konradkluz.websourceviewer.di;

import android.app.Application;

import com.konradkluz.websourceviewer.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by konradkluz on 17/10/2017.
 */
@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        ActivityBuilder.class
})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(App target);
}
