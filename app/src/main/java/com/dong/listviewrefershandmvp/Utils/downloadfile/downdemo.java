package com.dong.listviewrefershandmvp.Utils.downloadfile;

import android.os.Environment;
import android.util.Log;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2016/8/8.
 */
public class downdemo {
    public void downloadApk(String url) {
        // url="http://192.168.2.35:8080/gas/ceshi.apk";
        int index = url.lastIndexOf("/") + 1;
        String fileName = url.substring(index);
        String baseUrl = url.substring(0, index);
        //String fileName = "app-debug.apk";

        String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File
                .separator + "M_DEFAULT_DIR";
        String fileStoreName = fileName;
        FileApi.getInstance(baseUrl)//url.substring(0,index)
                .loadFileByName(fileName, new FileCallback(fileStoreDir, fileStoreName) {
                    @Override
                    public void onSuccess(File file) {
                        super.onSuccess(file);
                        //StartPresenter.this.getMvpView().installApp(file);
                    }

                    @Override
                    public void progress(long progress, long total) {
                        Log.d("tag", total + "");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        //StartPresenter.this.getMvpView().displayFrameworkBugMessage("下载失败！");
                        call.cancel();
                    }
                });
    }
}
