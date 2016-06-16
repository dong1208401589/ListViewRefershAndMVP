package com.dong.listviewrefershandmvp.base;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dong.listviewrefershandmvp.R;

import butterknife.BindView;

/**
 * Created by chenwendong on 2016/5/27.
 */
public abstract class BaseToolbarActivity extends BaseAppCompatActivity {

    @BindView(R.id.app_bar_layout) protected AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar) protected Toolbar mToolbar;

    protected ActionBarHelper mActionBarHelper;

    @Override
    protected void initToolBar(Bundle savedInstanceState) {
        this.initToolbarHelper();
    }

    protected void initToolbarHelper(){
        if(mToolbar==null||mAppBarLayout==null) return;

        this.setSupportActionBar(mToolbar);

        mActionBarHelper =createActionBarHelper();
        mActionBarHelper.init();

        if(Build.VERSION.SDK_INT>=21){
            mAppBarLayout.setElevation(6.6f);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.onBackPressed();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected void showBack(){
        if(mActionBarHelper!=null){
            mActionBarHelper.setDisplayHomeAsUpEnabled(true);
        }
    }

    public ActionBarHelper createActionBarHelper(){
        return new ActionBarHelper();
    }

    public class ActionBarHelper{
        private final ActionBar mActionBar;
        protected CharSequence mDrawerTitle;
        protected CharSequence mTitle;

        public ActionBarHelper(){
            this.mActionBar=getSupportActionBar();
        }

        public void init(){
            if(this.mActionBar==null) return;
            this.mActionBar.setDisplayHomeAsUpEnabled(true);// // 给左上角图标的左边加上一个返回的图标
            mActionBar.setDisplayShowHomeEnabled(false);// //使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标，对应id为Android.R.id.home，对应ActionBar.DISPLAY_SHOW_HOME
            mTitle=mDrawerTitle=getTitle();
        }

        public void onDrawerClose(){
            if(mActionBar==null) return;
            mActionBar.setTitle(mTitle);
        }

        public void onDrawerOpen(){
            if(mActionBar==null)return;
            mActionBar.setTitle(mDrawerTitle);
        }

        public void setTitle(CharSequence title){
            mTitle=title;
        }

        public void setDrawerTitle(CharSequence drawerTitle){
            mDrawerTitle=drawerTitle;
        }

        public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp){
            if(mActionBar==null) return;
                mActionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
        }
    }

}
