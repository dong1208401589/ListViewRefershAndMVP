package com.dong.listviewrefershandmvp.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by dong1 on 2016/7/2.
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder{

    /**
     * 用于保存findViewById加载过的view
     */
    private SparseArray<View> views;
    private View convertView;

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        views=new SparseArray<>();
        convertView=itemView;
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * 设置item的点击事件
     * @param listener
     * @param position
     */
    public void setOnItemClickListener(final BaseRecyclerViewHolder.OnItemClickListener listener,final int position){
        if(listener==null)
        {
            this.itemView.setOnClickListener(null);
        }else {
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v,position);
                }
            });
        }
    }

    public void setOnItemLongClickListener(final BaseRecyclerViewHolder.OnItemLongClickListener listener,final int position){
        if(listener==null)
            this.itemView.setOnLongClickListener(null);
        else {
            this.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return listener.onItemLongClick(v,position);
                }
            });
        }
    }
    /**
     * Due to the findViewById performance too low
     * The findViewById view will be cached, provide the findViewById next time the same view
     * ViewHolder model for the View
     * 由于findViewById性能过低
     * findViewById过的view会被缓存下来，以供下次find相同view的时候
     * ViewHolder模式 查找子View
     *
     * @param viewId viewId
     * @param <T> T
     * @return T
     */
    public <T extends View> T findViewById(int viewId){
        View view =views.get(viewId);
        if(view==null){
            view =convertView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }


    /**
     *点击事件回调
     */
    public interface OnItemClickListener{

        void onItemClick(View convertView,int position);
    }

    /**
     * 长点击事件
     */
    public interface OnItemLongClickListener{
        boolean onItemLongClick(View convertView,int position);
    }

}
