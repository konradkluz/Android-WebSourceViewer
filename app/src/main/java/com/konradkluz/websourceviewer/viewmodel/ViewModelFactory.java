package com.konradkluz.websourceviewer.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.konradkluz.websourceviewer.ui.MainActivityViewModel;

import javax.inject.Inject;

/**
 * Created by konradkluz on 17/10/2017.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private MainActivityViewModel mMainActivityViewModel;

    @Inject
    public ViewModelFactory(MainActivityViewModel mainActivityViewModel) {
        mMainActivityViewModel = mainActivityViewModel;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
            return (T) mMainActivityViewModel;
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
