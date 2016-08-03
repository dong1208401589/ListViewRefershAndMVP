package com.dong.listviewrefershandmvp.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.dong.listviewrefershandmvp.R;
import com.dong.listviewrefershandmvp.Utils.DialogHelp;
import com.dong.listviewrefershandmvp.Utils.ToastUtils;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/22.
 */
public abstract class BaseFragment extends Fragment {
    private ProgressDialog _waitDialog;
    private boolean _isVisible;
    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    public static final int STATE_NOMORE = 3;
    public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
    public static int mState = STATE_NONE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflater.inflate(getLayoutId(),null);
        return inflater.inflate(getLayoutId(),null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        _isVisible = true;

        initViews(savedInstanceState);
        initData();
        initListeners();

    }

    protected abstract int getLayoutId();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initListeners();



    public ProgressDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }


    public ProgressDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }


    public ProgressDialog showWaitDialog(String message) {
        if (_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelp.getWaitDialog(getActivity(), message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }


    public void hideWaitDialog() {
        if (_isVisible && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void dialogShow(String msg) {
        // camera error
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
        builder.show();
    }
    /*********
     * Toast *
     *********/

    public void showToast(String msg) {
        this.showToast(msg, Toast.LENGTH_SHORT);
    }


    public void showToast(String msg, int duration) {
        if (msg == null) return;
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            ToastUtils.show(getActivity(), msg, duration);
        } else {
            ToastUtils.show(getActivity(), msg, ToastUtils.LENGTH_SHORT);
        }
    }


    public void showToast(int resId) {
        this.showToast(resId, Toast.LENGTH_SHORT);
    }


    public void showToast(int resId, int duration) {
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            ToastUtils.show(getActivity(), resId, duration);
        } else {
            ToastUtils.show(getActivity(), resId, ToastUtils.LENGTH_SHORT);
        }
    }

    /*********
     * Toast *
     *********/
}
