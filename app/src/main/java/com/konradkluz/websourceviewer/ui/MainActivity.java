package com.konradkluz.websourceviewer.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mMainActivityViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(MainActivityViewModel.class);

        observeLoadingStatus();
        observeResponse();

    }

    @OnClick(R.id.fab)
    void onSearchButtonClick() {
        String url = mEditText.getText().toString();
        mMainActivityViewModel.loadPageSource(url);
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
                mTextView.setText(response.data);
                Timber.d("Response received with source: %s", response.data);
                break;
            case ERROR_SERVER:
                Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show();
                Timber.e(response.error);
                break;
            case ERROR_NETWORK:
                Toast.makeText(this, "Network connection error", Toast.LENGTH_SHORT).show();
                Timber.e(response.error);
                break;
            case ERROR_OTHER:
                Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();
                Timber.e(response.error);
                break;
            case ERROR_VALIDATION:
                Toast.makeText(this, "Wrong URL. Please type correct one.", Toast.LENGTH_SHORT).show();
                Timber.w("Wrong URL: %s", response.data);
                break;
        }
    }
}
