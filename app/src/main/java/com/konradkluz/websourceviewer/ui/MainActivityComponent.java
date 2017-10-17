package com.konradkluz.websourceviewer.ui;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by konradkluz on 17/10/2017.
 */
@MainActivityScope
@Subcomponent(modules = {MainActivityModule.class})
public interface MainActivityComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity>{}

}
