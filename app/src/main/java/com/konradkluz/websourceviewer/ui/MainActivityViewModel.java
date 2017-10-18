package com.konradkluz.websourceviewer.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Patterns;

import com.konradkluz.websourceviewer.model.entities.Response;
import com.konradkluz.websourceviewer.model.repository.RemoteRepository;
import com.konradkluz.websourceviewer.rx.SchedulersFacade;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by konradkluz on 17/10/2017.
 */

public class MainActivityViewModel extends ViewModel {

    @Inject
    @Named("responseLiveData")
    MutableLiveData<Response<String>> response;

    @Inject
    @Named("loadingStatusLiveData")
    MutableLiveData<Boolean> loadingStatus;

    private CompositeDisposable compositeDisposable;
    private RemoteRepository mRemoteRepository;
    private SchedulersFacade mSchedulersFacade;

    @Inject
    public MainActivityViewModel(RemoteRepository remoteRepository,
                                 SchedulersFacade schedulersFacade) {
        mRemoteRepository = remoteRepository;
        mSchedulersFacade = schedulersFacade;
        compositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData<Response<String>> getResponse() {
        return response;
    }

    public MutableLiveData<Boolean> getLoadingStatus() {
        return loadingStatus;
    }

    public void loadPageSource(String url) {
        if (!Patterns.WEB_URL.matcher(url).matches() ||
                (!url.startsWith("http") && !url.startsWith("https"))) {
            response.setValue(Response.wrongUrl(url));
            return;
        }
        compositeDisposable.add(mRemoteRepository
                .getPageSource(url,
                        mSchedulersFacade.io(),
                        mSchedulersFacade.ui()
                )
                .doOnSubscribe(s -> loadingStatus.setValue(true))
                .doAfterTerminate(() -> loadingStatus.setValue(false))
                .subscribe(s -> {
                            response.setValue(Response.success(s));
                        },
                        error -> response.setValue(Response.error(error)))
        );
    }

    @Override
    protected void onCleared() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }
}
