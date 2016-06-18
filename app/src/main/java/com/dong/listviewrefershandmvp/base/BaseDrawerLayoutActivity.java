package com.dong.listviewrefershandmvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.dong.listviewrefershandmvp.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by dong1 on 2016/6/18.
 */
public abstract class BaseDrawerLayoutActivity extends BaseSwipeRefreshLayoutActivity {

    @BindView(R.id.root_view) protected DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view) protected NavigationView mNavigation;

    private ActionBarDrawerToggle mDrawerToggle;

    protected HashMap<Integer,MenuItem> mMenuItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getNavigationItemSelectedListener()!=null){
            mNavigation.setNavigationItemSelectedListener(getNavigationItemSelectedListener());
        }

        mDrawerLayout.addDrawerListener(new MyDrawerListener());

        //初始化MenuItems
        this.mMenuItems=new HashMap<>();
        int[] menunItemIds=getMenuItemIds();
        if(menunItemIds.length>0){
            for(int id : menunItemIds){
                MenuItem item = this.mNavigation.getMenu().findItem(id);
                if(item!=null) this.mMenuItems.put(id,item);
            }
        }
        mDrawerToggle =new ActionBarDrawerToggle(this,mDrawerLayout,R.string.app_name,R.string.app_name);
        //mDrawerToggle.setHomeAsUpIndicator();
    }

    /**
        走到这，不会有两次点击一样的情况
     */
    public abstract void onMenuItemClick(MenuItem now);

    public boolean menuItemChecked(int menuId){
        MenuItem old=null;
        MenuItem now;
        if(mMenuItems.containsKey(menuId)){
            for (Map.Entry<Integer,MenuItem> entry:mMenuItems.entrySet()){
                MenuItem item = entry.getValue();
                if(item.isChecked()){
                    old=item;
                }

                if(old !=null && old.getItemId()==menuId) break;

                if(item.getItemId()==menuId){
                    now=item;
                    now.setChecked(true);
                    onMenuItemClick(now);
                }else {
                    item.setChecked(false);
                }
            }
            mDrawerLayout.closeDrawer(mNavigation);
            return true;

        }else return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && mDrawerLayout.isDrawerOpen(mNavigation)){
            mDrawerLayout.closeDrawer(mNavigation);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract int[] getMenuItemIds();

    public abstract NavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener();

    private class MyDrawerListener implements DrawerLayout.DrawerListener{

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            BaseDrawerLayoutActivity.this.mDrawerToggle.onDrawerSlide(drawerView,slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            BaseDrawerLayoutActivity.this.mDrawerToggle.onDrawerOpened(drawerView);
            if(BaseDrawerLayoutActivity.this.mActionBarHelper!=null){
                BaseDrawerLayoutActivity.this.mActionBarHelper.onDrawerOpen();
            }
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            BaseDrawerLayoutActivity.this.mDrawerToggle.onDrawerClosed(drawerView);
            BaseDrawerLayoutActivity.this.mActionBarHelper.onDrawerClose();
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            BaseDrawerLayoutActivity.this.mDrawerToggle.onDrawerStateChanged(newState);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //指示，ActionBarDrawerToggle与DrawerLayout的状态同步，并将ActionBarDrawerToggle中的drawer图标，设置为ActionBar的Home-Button的icon
       //该方法会自动和actionBar关联, 将开关的图片显示在了action上，如果不设置，也可以有抽屉的效果，不过是默认的图标
        mDrawerToggle.syncState();
    }
}
