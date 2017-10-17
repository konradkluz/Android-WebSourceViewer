package com.konradkluz.websourceviewer.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.konradkluz.websourceviewer.R;
import com.konradkluz.websourceviewer.model.entities.Response;
import com.konradkluz.websourceviewer.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory mViewModelFactory;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.pb_loading_indicator)
    ProgressBar mProgressBar;

    @BindView(R.id.et_url)
    EditText mEditText;

    @BindView(R.id.tv_response)
    TextView mTextView;

    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mMainActivityViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(MainActivityViewModel.class);

    }

    @OnClick(R.id.fab)
    void onSearchButtonClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void observeLoadingStatus() {
        mMainActivityViewModel.getLoadingStatus().observe(this, isLoading -> {
            mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
    }

    private void observeResponse() {
        mMainActivityViewModel.getResponse().observe(this, this::processResponse);
    }

    private void processResponse(Response<String> response) {
        switch (response.status) {
            case SUCCESS:
                break;
            case ERROR_SERVER:
                break;
            case ERROR_NETWORK:
                break;
            case ERROR_OTHER:
                break;
        }
    }
}
