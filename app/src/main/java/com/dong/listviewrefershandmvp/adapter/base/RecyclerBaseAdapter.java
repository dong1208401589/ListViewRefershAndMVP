package com.dong.listviewrefershandmvp.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dong.listviewrefershandmvp.R;
import com.dong.listviewrefershandmvp.Utils.TDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public abstract class RecyclerBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int STATE_EMPTY_ITEM=0;
    public static final int STATE_LOAD_MORE=1;
    public static final int STATE_NO_MORE=2;
    public static final int STATE_NO_DATA=3;
    public static final int STATE_LESS_ONE_PAGE=4;
    public static final int STATE_NETWORK_ERROR=5;
    public static final int STATE_OTHER=6;

    public static final int LODE_VIEW_TYPE=7;
    public static final int CONTENT_VIEW_TYPE=8;

    protected int state=STATE_LESS_ONE_PAGE;


    private BaseRecyclerViewHolder.OnItemClickListener onItemClickListener;
    private BaseRecyclerViewHolder.OnItemLongClickListener onItemLongClickListener;


    protected int _loadmoreText;
    protected int _loadFinishText;
    protected int _noDataText;

    public void setState(int state){
        this.state=state;
    }

    public int getState(){
        return this.state;
    }

    protected ArrayList<T> mDatas=new ArrayList<T>();

    public RecyclerBaseAdapter(){
        _loadFinishText= R.string.loading_no_more;
        _loadmoreText=R.string.loading;
        _noDataText=R.string.error_view_no_data;
    }

    /**
     * Please return RecyclerView loading layout Id array
     * 请返回RecyclerView加载的布局Id数组
     *
     * @return 布局Id数组
     */
    public abstract int[] getItemLayouts();

    /**
     * butt joint the onBindViewHolder and
     * If you want to write logic in onBindViewHolder, you can write here
     * 对接了onBindViewHolder
     * onBindViewHolder里的逻辑写在这
     *
     * @param viewHolder viewHolder
     * @param position position
     */
    public abstract void onBindRecycleViewHolder(BaseRecyclerViewHolder viewHolder, int position);

    /**
     * Please write judgment logic when more layout
     * and not write when single layout
     * 如果是多布局的话，请写判断逻辑
     * 单布局可以不写
     *
     * @param position Item position
     * @return 布局Id数组中的index
     */
    public abstract int getRecycleViewItemType(int position);


    /**
     * get the itemType by position
     * 根据position获取ItemType
     *
     * @param position Item position
     * @return 默认ItemType等于0
     */
    @Override public int getItemViewType(int position) {
        if(position==getItemCount()-1&&hasFootView())//最后一条
        {
            return LODE_VIEW_TYPE;
        }else {
            return this.getRecycleViewItemType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType<0)return null;
        if(getItemLayouts()==null) return null;
        int[] layoutIds=this.getItemLayouts();
        if(layoutIds.length<1)return null;
        int itemLayoutId;
        if(layoutIds.length==1){
            itemLayoutId = layoutIds[0];
        }else if (viewType==LODE_VIEW_TYPE){
            itemLayoutId=R.layout.list_cell_footer;
        }
        else {
            itemLayoutId = layoutIds[viewType];
        }

        View view=LayoutInflater.from(parent.getContext()).inflate(itemLayoutId,null);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new BaseRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            BaseRecyclerViewHolder baseRecyclerViewHolder= (BaseRecyclerViewHolder) holder;
            if (getItemViewType(position)==LODE_VIEW_TYPE){
                if(getState()==STATE_LOAD_MORE||getState()==STATE_NO_MORE||getState()
                        ==STATE_EMPTY_ITEM|| getState()==STATE_NETWORK_ERROR) {
                    mFooterView = (LinearLayout) baseRecyclerViewHolder.getConvertView();
                    if (!loadMoreHasBg()) {
                        mFooterView.setBackgroundDrawable(null);
                    }
                    ProgressBar progress = baseRecyclerViewHolder.findViewById(R.id.progressbar);
                    TextView text = baseRecyclerViewHolder.findViewById(R.id.textview);
                    switch (getState()) {
                        case STATE_LOAD_MORE:
                            setFooterViewLoading();
                            break;
                        case STATE_NO_MORE:
                            mFooterView.setVisibility(View.VISIBLE);
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
                            if (TDevice.hasInternet()) {
                                text.setText("数据不见了");
                            } else {
                                text.setText("没有可用的网络");
                            }
                            break;
                        default:
                            mFooterView.setVisibility(View.GONE);
                            progress.setVisibility(View.GONE);
                            text.setVisibility(View.GONE);
                            break;
                    }
                }
            }
            this.onBindViewHolder(baseRecyclerViewHolder,position);
            baseRecyclerViewHolder.setOnItemClickListener(onItemClickListener,position);
            baseRecyclerViewHolder.setOnItemLongClickListener(onItemLongClickListener,position);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
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


    public void setOnItemClickListener(BaseRecyclerViewHolder.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(BaseRecyclerViewHolder.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
