package com.konradkluz.websourceviewer.model.api;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by konradkluz on 17/10/2017.
 */
public interface PageSourceService {

    @GET
    Single<ResponseBody> getPageSource(@Url String dynamicUrl);

}
