package com.dong.listviewrefershandmvp.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.dong.listviewrefershandmvp.R;
import com.dong.listviewrefershandmvp.adapter.RecylclerBaseAdapter;
import com.dong.listviewrefershandmvp.base.BaseDrawerLayoutActivity;

import butterknife.BindView;

public class MainActivity extends BaseDrawerLayoutActivity {

    @BindView(R.id.main_rv)
    protected RecyclerView rv;

    RecylclerBaseAdapter adapter;


    @Override
    public void onSwipeRefresh() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        //this.showBack();
        this.setTitle("呵呵");
        adapter=new RecylclerBaseAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public void onMenuItemClick(MenuItem now) {

    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_about:
                //this.refreshWebView();
                Intent intent=new Intent(this,TestMoveImageViewActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_main_home_page:
                //DeviceUtils.copy2Clipboard(this, this.webview.getUrl());
                //Snackbar.make(this.webview, this.getString(R.string.common_copy_success),
                       // Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.menu_main_top_github:
                //ShareUtils.share(this, this.getUrl());
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int[] getMenuItemIds() {
        return new int[0];
    }

    @Override
    public NavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener() {
        return null;
    }

    @Override
    protected void initListeners() {

    }
}
