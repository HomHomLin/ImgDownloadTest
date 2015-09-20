package com.lhh.imgdt.imgdownloadtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

    public ImageView mImg;

    public ProgressBar mPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = (Bitmap)msg.obj;
            mImg.setImageBitmap(bitmap);
            mPb.setVisibility(View.GONE);
        }
    };

    public void findViews(){
        mPb = (ProgressBar)findViewById(R.id.pbLoading);
        mImg = (ImageView) findViewById(R.id.ivImg);
        ImageDownloader imageDownloader = new ImageDownloader();
        imageDownloader.download("http://ww1.sinaimg.cn/bmiddle/6630ff83gw1ew2yuv8jrgj217815ywid.jpg", new ImageDownloader.ImageDownloaderCallBack() {
            @Override
            public void onStart(String url) {

            }

            @Override
            public void onSuccess(String url, int code, Bitmap bitmap) {
                Message msg = handler.obtainMessage(0, bitmap);
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(String url, int code) {

            }
        });
    }

}
