package com.konradkluz.websourceviewer.ui;

import android.arch.lifecycle.MutableLiveData;

import com.konradkluz.websourceviewer.model.entities.Response;
import com.konradkluz.websourceviewer.model.repository.RemoteRepositoryImpl;
import com.konradkluz.websourceviewer.rx.SchedulersFacade;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by konradkluz on 17/10/2017.
 */

public class MainActivityViewModelTest {

    private MainActivityViewModel mMainActivityViewModel;
    private RemoteRepositoryImpl mRemoteRepository;
    private SchedulersFacade mSchedulersFacade;
    private MutableLiveData<Response<String>> mResponse;
    private MutableLiveData<Boolean> mLoadingStatus;

    @Before
    public void setup() throws Exception {
        mResponse = mock(MutableLiveData.class);
        mLoadingStatus = mock(MutableLiveData.class);
        mRemoteRepository = mock(RemoteRepositoryImpl.class);
        mSchedulersFacade = mock(SchedulersFacade.class);
        mMainActivityViewModel = new MainActivityViewModel(mRemoteRepository, mSchedulersFacade);
        mMainActivityViewModel.response = mResponse;
        mMainActivityViewModel.loadingStatus = mLoadingStatus;
    }

    @Test
    public void loadPageSourceWithCorrectResponseShouldSetProperValueToResponseLiveData() throws Exception {
        //given
        TestScheduler testScheduler = new TestScheduler();
        String url = "test_url";
        String response = "test_response";

        //when
        when(mSchedulersFacade.io()).thenReturn(testScheduler);
        when(mSchedulersFacade.ui()).thenReturn(testScheduler);
        when(mRemoteRepository.getPageSource(url, mSchedulersFacade.io(), mSchedulersFacade.ui()))
                .thenReturn(Single.just(response));
        //and
        mMainActivityViewModel.loadPageSource(url);

        //then
        verify(mResponse, times(1)).setValue(Response.success(response));
    }

    @Test
    public void loadPageSourceWithErrorResponseShouldSetResponseWithErrorToLiveData() {
        //given
        TestScheduler testScheduler = new TestScheduler();
        String url = "test_url";
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("text"), "error");
        HttpException httpException = new HttpException(retrofit2.Response.error(500, responseBody));

        //when
        when(mSchedulersFacade.io()).thenReturn(testScheduler);
        when(mSchedulersFacade.ui()).thenReturn(testScheduler);
        when(mRemoteRepository.getPageSource(url, mSchedulersFacade.io(), mSchedulersFacade.ui()))
                .thenReturn(Single.error(httpException));
        //and
        mMainActivityViewModel.loadPageSource(url);

        //then
        verify(mResponse, times(1)).setValue(Response.error(httpException));
    }
}
