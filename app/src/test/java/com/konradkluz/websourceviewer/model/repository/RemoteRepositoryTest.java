package com.konradkluz.websourceviewer.model.repository;

import com.konradkluz.websourceviewer.model.api.PageSourceService;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by konradkluz on 17/10/2017.
 */

public class RemoteRepositoryTest {

    private RemoteRepositoryImpl mRemoteRepository;

    private PageSourceService mPageSourceService;

    @Before
    public void setup() {
        mRemoteRepository = new RemoteRepositoryImpl();
        mPageSourceService = mock(PageSourceService.class);
        mRemoteRepository.mPageSourceService = mPageSourceService;
    }

    @Test
    public void getPageSourceShouldReturnSingleWithPageSourceString() throws Exception {
        //given
        TestScheduler testScheduler = new TestScheduler();
        String url = "test_url";
        String response = "test_response_string";
        //and
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("text"), response);
        Single<ResponseBody> responseBodySingle = Single.just(responseBody);

        //when
        when(mPageSourceService.getPageSource(url)).thenReturn(responseBodySingle);
        Single<String> pageSource = mRemoteRepository.getPageSource(url, testScheduler, testScheduler);

        //then
        TestObserver<String> testObserver = pageSource.test();
        testScheduler.triggerActions();
        testObserver.assertNoErrors();
        testObserver.assertValue(response);
    }
}
