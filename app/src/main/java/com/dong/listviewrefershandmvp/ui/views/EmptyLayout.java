package com.dong.listviewrefershandmvp.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dong.listviewrefershandmvp.R;
import com.dong.listviewrefershandmvp.Utils.TDevice;

/**
 * Created by Administrator on 2016/6/15.
 */
public class EmptyLayout extends LinearLayout implements View.OnClickListener{

    public static final int HIDE_LAYOUT=0;
    public static final int NETWORK_ERROR=1;
    public static final int NETWORK_LOADING=2;
    public static final int NODATA=3;
    public static final int NODATA_ENABLE_CLICK=4;
    public static final int LOGIN=5;

    private ProgressBar progressBar;
    private boolean clickEnable=true;
    private final Context context;

    public ImageView img;
    private OnClickListener listener;
    private int myErrorState;
    private String noDataContent="";
    private TextView tv;


    public EmptyLayout(Context context) {
        super(context);
        this.context=context;
        init();

    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public void init(){
        View view = View.inflate(context,R.layout.list_cell_footer,null);
        img= (ImageView) view.findViewById(R.id.img_error_layout);
        tv= (TextView) view.findViewById(R.id.tv_error_layout);
        progressBar = (ProgressBar) view.findViewById(R.id.animprogress);
        setBackgroundColor(-1);
        setOnClickListener(this);
        img.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if(clickEnable){
                    if(listener!=null)
                    listener.onClick(v);
                }
            }
        });
        addView(view);
    }

    public void dismiss(){
        myErrorState=HIDE_LAYOUT;
        setVisibility(GONE);
    }

    public int getErrorState(){
        return myErrorState;
    }

    public boolean isLoadError(){
        return myErrorState==NETWORK_ERROR;
    }

    public boolean isLoading(){
        return myErrorState==NETWORK_LOADING;
    }

    @Override
    public void onClick(View v) {
        if (clickEnable){
            if (listener!=null){
                listener.onClick(v);
            }
        }
    }

    public void setErrorMessage(String msg){
        tv.setText(msg);
    }

    public void setErrorImage(int imageResource){
        try{
            img.setImageResource(imageResource);
        }catch (Exception e) {

        }
    }

    public void setErrorType(int type){
        switch (myErrorState){
            case NETWORK_ERROR:
                myErrorState=NETWORK_ERROR;
                if(TDevice.hasInternet()){
                    tv.setText(R.string.error_view_load_error_click_to_refresh);
                    img.setBackgroundResource(R.mipmap.pagefailed_bg);
                }else
                {
                    tv.setText(R.string.error_view_network_error_click_to_refresh);
                    img.setBackgroundResource(R.mipmap.page_icon_network);
                }
                img.setVisibility(VISIBLE);
                progressBar.setVisibility(GONE);
                clickEnable=true;
                break;
            case NETWORK_LOADING:
                myErrorState=NETWORK_LOADING;
                progressBar.setVisibility(VISIBLE);
                img.setVisibility(GONE);
                tv.setText(R.string.loading);
                clickEnable=false;
                break;
            case NODATA:
                myErrorState=NODATA;
                img.setImageResource(R.mipmap.page_icon_empty);
                img.setVisibility(VISIBLE);
                progressBar.setVisibility(GONE);
                setTvNoDataContent();
                clickEnable=true;
                break;
            case HIDE_LAYOUT:
                setVisibility(GONE);
                break;
            case NODATA_ENABLE_CLICK:
                myErrorState=NODATA_ENABLE_CLICK;
                img.setImageResource(R.mipmap.page_icon_empty);
                img.setVisibility(VISIBLE);
                progressBar.setVisibility(GONE);
                setTvNoDataContent();
                clickEnable=true;
                break;
            default:
                break;
        }
    }

    public void setNoDataContent(String noDataContent){
        this.noDataContent=noDataContent;
    }

    public void setTvNoDataContent(){
        if(!noDataContent.equals("")){
            tv.setText(noDataContent);
        }else
            tv.setText(R.string.error_view_no_data);
    }

    public void setOnLayoutClickListener(OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void setVisibility(int visibility){
        if(visibility==GONE)
            myErrorState=HIDE_LAYOUT;
        super.setVisibility(visibility);
    }
}
