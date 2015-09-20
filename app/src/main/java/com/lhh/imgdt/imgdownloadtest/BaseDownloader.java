package com.lhh.imgdt.imgdownloadtest;

import java.io.InputStream;

/**
 * Created by Linhh on 15/9/20.
 */
public interface BaseDownloader {

    public static final int DOWNLOAD_CODE_NULL_URL = -1;

    public static final int DOWNLOAD_CODE_UNKNOW_EXCEPTION = -2;

    public interface DownloaderCallBack{

        public void onStart(String url);

        public void onSuccess(String url, int code, InputStream inputStream);

        public void onFailure(String url, int code);
    }
}
