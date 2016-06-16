package com.dong.listviewrefershandmvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/27.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayoutId());
        ButterKnife.bind(this);

        initToolBar(savedInstanceState);
        initViews(savedInstanceState);
        initData();
        initListeners();
    }

    protected abstract int getLayoutId();

    protected abstract void initToolBar(Bundle savedInstanceState);

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initListeners();
}
