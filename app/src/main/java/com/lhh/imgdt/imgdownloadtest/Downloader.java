package com.lhh.imgdt.imgdownloadtest;

import android.text.TextUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Linhh on 15/9/20.
 */
public class Downloader{

    private static final String TAG = "Downloader";

    private static Downloader mDownloaderInstance;

    public static Downloader getInstance(){
        if(mDownloaderInstance == null){
            mDownloaderInstance = new Downloader();
        }
        return mDownloaderInstance;
    }

    private Downloader() {

    }

    public void relaseInstance(){
        mDownloaderInstance = null;
    }

    public void download(String url, BaseDownloader.DownloaderCallBack callBack) {
        new Thread(new DownloaderRunnable(url, callBack)).start();
    }

    class DownloaderRunnable implements Runnable{

        private String mUrl;

        private BaseDownloader.DownloaderCallBack mCallBack;

        public DownloaderRunnable(String url, BaseDownloader.DownloaderCallBack callBack){

            this.mUrl = url;

            this.mCallBack = callBack;
        }

        @Override
        public void run() {
            try {

                //开始下载的回调
                if(mCallBack != null) {
                    mCallBack.onStart(mUrl);
                }

                if(TextUtils.isEmpty(mUrl) && mCallBack != null){
                    mCallBack.onFailure(mUrl, BaseDownloader.DOWNLOAD_CODE_NULL_URL);
                }

                URL url = new URL(mUrl);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                conn.setDoInput(true);

                conn.connect();

                InputStream is = url.openStream();

                if(mCallBack != null) {

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        mCallBack.onSuccess(mUrl, conn.getResponseCode(), is);

                    } else {

                        mCallBack.onFailure(mUrl, conn.getResponseCode());

                    }
                }

            } catch (Exception e) {

                e.printStackTrace();

                if(mCallBack != null) {

                    mCallBack.onFailure(mUrl, BaseDownloader.DOWNLOAD_CODE_UNKNOW_EXCEPTION);
                }

            }
        }
    }
}
