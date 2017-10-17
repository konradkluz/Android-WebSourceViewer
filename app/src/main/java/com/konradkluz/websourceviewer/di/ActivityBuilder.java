package com.konradkluz.websourceviewer.di;

import android.app.Activity;

import com.konradkluz.websourceviewer.ui.MainActivity;
import com.konradkluz.websourceviewer.ui.MainActivityComponent;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by konradkluz on 17/10/2017.
 */
@Module
public abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(MainActivityComponent.Builder builder);

}
