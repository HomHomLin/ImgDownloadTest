package com.lhh.imgdt.imgdownloadtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Linhh on 15/9/20.
 */
public class ImageDownloader  implements BaseDownloader{

    private boolean mSaveCache = false;//是否自动保存图片

    public interface ImageDownloaderCallBack{

        public void onStart(String url);

        public void onSuccess(String url, int code, Bitmap bitmap);

        public void onFailure(String url, int code);
    }

    public ImageDownloader(){

    }

    public void download(String url) {
        this.download(url, null);
    }

    class DefalutDownloaderCallBack implements DownloaderCallBack{

        private ImageDownloaderCallBack mImageDownloaderCallBack;

        public DefalutDownloaderCallBack(ImageDownloaderCallBack callBack){
            mImageDownloaderCallBack = callBack;
        }

        @Override
        public void onStart(String url) {
            if(mImageDownloaderCallBack != null)
                mImageDownloaderCallBack.onStart(url);
        }

        @Override
        public void onSuccess(String url, int code, InputStream inputStream) {
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            if(mImageDownloaderCallBack != null)
                mImageDownloaderCallBack.onSuccess(url, code, bitmap);

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(String url, int code) {
            if(mImageDownloaderCallBack != null)
                mImageDownloaderCallBack.onFailure(url, code);
        }
    }

    public void download(String url, final ImageDownloaderCallBack callBack) {

        Downloader.getInstance().download(url, new DefalutDownloaderCallBack(callBack));

    }

    public boolean ismSaveCache() {
        return mSaveCache;
    }

    public void setmSaveCache(boolean mSaveCache) {
        this.mSaveCache = mSaveCache;
    }

}
