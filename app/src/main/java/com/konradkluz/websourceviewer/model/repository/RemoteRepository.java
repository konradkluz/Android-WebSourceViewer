package com.konradkluz.websourceviewer.model.repository;

import io.reactivex.Scheduler;
import io.reactivex.Single;

/**
 * Created by konradkluz on 17/10/2017.
 */
public interface RemoteRepository {

    Single<String> getPageSource(String url, Scheduler subscribeScheduler, Scheduler observeScheduler);

}
