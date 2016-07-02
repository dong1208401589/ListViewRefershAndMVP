package com.dong.listviewrefershandmvp.ui.activity;

import android.os.Bundle;

import com.dong.listviewrefershandmvp.R;
import com.dong.listviewrefershandmvp.base.BaseAppCompatActivity;
import com.dong.listviewrefershandmvp.ui.views.testVIews.ScrollerSliding;

import butterknife.BindView;

/**
 * Created by dong1 on 2016/6/27.
 */
public class TestMoveImageViewActivity extends BaseAppCompatActivity {

    @BindView(R.id.ssview) protected ScrollerSliding ssv;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_move_image_vew;
    }

    @Override
    protected void initToolBar(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListeners() {

    }
}
