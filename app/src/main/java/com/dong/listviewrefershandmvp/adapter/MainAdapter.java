package com.dong.listviewrefershandmvp.adapter;

import com.dong.listviewrefershandmvp.adapter.base.BaseRecyclerViewHolder;
import com.dong.listviewrefershandmvp.adapter.base.RecyclerBaseAdapter;

/**
 * Created by Administrator on 2016/6/16.
 */
public class MainAdapter extends RecyclerBaseAdapter{
    @Override
    public int[] getItemLayouts() {
        return new int[0];
    }

    @Override
    public void onBindRecycleViewHolder(BaseRecyclerViewHolder viewHolder, int position) {

    }

    @Override
    public int getRecycleViewItemType(int position) {
        return 0;
    }
}
