package com.dong.listviewrefershandmvp.adapter.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.dong.listviewrefershandmvp.R;
import com.dong.listviewrefershandmvp.Utils.TDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14.
 */
public class ListBaseAdapter<T> extends BaseAdapter {

    public static final int STATE_EMPTY_ITEM=0;
    public static final int STATE_LOAD_MORE=1;
    public static final int STATE_NO_MORE=2;
    public static final int STATE_NO_DATA=3;
    public static final int STATE_LESS_ONE_PAGE=4;
    public static final int STATE_NETWORK_ERROR=5;
    public static final int STATE_OTHER=6;

    protected int state=STATE_LESS_ONE_PAGE;

    protected int _loadmoreText;
    protected int _loadFinishText;
    protected int _noDataText;
    //protected int mScreenWidth;

    private LayoutInflater mInflater;

    protected LayoutInflater getLayoutInflater(Context context){
        if(mInflater==null){
            mInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }

    public void setState(int state){
        this.state=state;
    }

    public int getState(){
        return this.state;
    }

    protected ArrayList<T> mDatas=new ArrayList<T>();

    public ListBaseAdapter(){
        _loadFinishText= R.string.loading_no_more;
        _loadmoreText=R.string.loading;
        _noDataText= R.string.error_view_no_data;
    }

    public View getFooterView() {
        return this.mFooterView;
    }
    @Override
    public int getCount() {
        switch (getState())
        {
            case STATE_EMPTY_ITEM:
                return getDataSizePlus1();
            case STATE_NETWORK_ERROR:
            case STATE_LOAD_MORE:
                return getDataSizePlus1();
            case STATE_NO_DATA:
                return 1;
            case STATE_NO_MORE:
                return getDataSizePlus1();
            case STATE_LESS_ONE_PAGE:
                return getDataSize();
            default:
                break;
        }
        return getDataSize();
    }

    public int getDataSizePlus1()
    {
        if(hasFootView()){
            return getDataSize()+1;
        }
        return getDataSize();
    }

    public int getDataSize(){
        return mDatas.size();
    }

    protected boolean hasFootView(){
        return true;
    }


    @Override
    public T getItem(int position) {
        if(getDataSize()>position){
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setDatas(ArrayList<T> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }

    public ArrayList<T> getDatas(){
        return mDatas == null ? (mDatas = new ArrayList<T>()) : mDatas;
    }

    public void addData(List<T> data){
        if(mDatas!=null&& data!=null && !data.isEmpty()){
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addItem(T obj){
        if(mDatas!=null){
            mDatas.add(obj);
        }
        notifyDataSetChanged();
    }

    public void addItem(int index,T obj){
        if(mDatas!=null){
            mDatas.add(index,obj);
        }
        notifyDataSetChanged();
    }

    public void removeItem(Object obj){
        mDatas.remove(obj);
        notifyDataSetChanged();
    }

    public void clear(){
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void setLoadmoreText(int loadmoreText){
        _loadmoreText=loadmoreText;
    }

    public void setloadFinishText(int loadFinishText){
        _loadFinishText=loadFinishText;
    }

    public void setNoDataText(int loadnoDataText){
        _noDataText=loadnoDataText;
    }

    protected boolean loadMoreHasBg(){
        return true;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position==getCount()-1&&hasFootView())//最后一条
        {
            if(getState()==STATE_LOAD_MORE||getState()==STATE_NO_MORE||getState()
                    ==STATE_EMPTY_ITEM|| getState()==STATE_NETWORK_ERROR){
                mFooterView= (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .list_cell_footer,null);
                if(!loadMoreHasBg()){
                    mFooterView.setBackgroundDrawable(null);
                }
                ProgressBar progress= (ProgressBar) mFooterView.findViewById(R.id.progressbar);
                TextView text= (TextView) mFooterView.findViewById(R.id.textview);
                switch (getState()){
                    case STATE_LOAD_MORE:
                        setFooterViewLoading();
                        break;
                    case STATE_NO_MORE:

                        if (position<20){
                            mFooterView.setVisibility(View.GONE);
                        }else  mFooterView.setVisibility(View.VISIBLE);

                        progress.setVisibility(View.GONE);
                        text.setText(_loadFinishText);
                        text.setVisibility(View.VISIBLE);
                        break;
                    case STATE_EMPTY_ITEM:
                        mFooterView.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        text.setText(_noDataText);
                        break;
                    case STATE_NETWORK_ERROR:
                        mFooterView.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        text.setVisibility(View.VISIBLE);
                        if(TDevice.hasInternet()){
                            text.setText("数据不见了");
                        }else {
                            text.setText("没有可用的网络");
                        }
                        break;
                    default:
                        mFooterView.setVisibility(View.GONE);
                        progress.setVisibility(View.GONE);
                        text.setVisibility(View.GONE);
                        break;
                }
                return mFooterView;
            }
        }

        if(position<0){
            position=0;
        }
        return getRealView(position,convertView,parent);
    }

    protected View getRealView(int position, View convertView, ViewGroup parent){
        return null;
    }

    private LinearLayout mFooterView;

    public View setFooterView(){
        return this.mFooterView;
    }
    public void setFooterViewLoading(String loadMsg){
        ProgressBar progress= (ProgressBar) mFooterView.findViewById(R.id.progressbar);
        TextView text= (TextView) mFooterView.findViewById(R.id.textview);

        mFooterView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        if(TextUtils.isEmpty(loadMsg)){
            text.setText(_loadmoreText);
        }else {
            text.setText(loadMsg);
        }
    }
    public void setFooterViewLoading(){
        setFooterViewLoading("");
    }

}
