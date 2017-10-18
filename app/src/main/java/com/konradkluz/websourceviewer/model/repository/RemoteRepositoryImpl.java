package com.konradkluz.websourceviewer.model.repository;

import com.konradkluz.websourceviewer.model.api.PageSourceService;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import okhttp3.ResponseBody;

/**
 * Created by konradkluz on 17/10/2017.
 */
public class RemoteRepositoryImpl implements RemoteRepository {

    @Inject
    PageSourceService mPageSourceService;

    @Inject
    public RemoteRepositoryImpl() {
    }

    @Override
    public Single<String> getPageSource(String url, Scheduler subscribeScheduler, Scheduler observeScheduler) {
        return mPageSourceService.getPageSource(url)
                .map(ResponseBody::string)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }

}
