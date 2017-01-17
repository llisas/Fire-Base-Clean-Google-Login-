package com.jesuscuevas.app.getmyfilm.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        getPresenter();
        init();

    }

    protected abstract void getPresenter();
    protected abstract int getLayout();
    protected abstract void init();
}
