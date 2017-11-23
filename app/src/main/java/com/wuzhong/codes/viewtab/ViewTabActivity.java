package com.wuzhong.codes.viewtab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.wuzhong.codes.R;

/**
 * 使用viewpager的tab
 * 需求:
 * 使用viewpager制作一个tab框架@
 * 第一页:点击按钮可以开始一个实时刷新的进度条
 * 第二页:点击按钮可以开始/暂停第一页的进度条
 *
 * Created by Wuzhong on 2017-10-24.
 */

public class ViewTabActivity extends AppCompatActivity {

    private GIFView mGifView ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tab_act_splash);
        mGifView = (GIFView) findViewById(R.id.gif_splash);
        mGifView.setMovieResource(R.mipmap.f7);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4500);
                    Intent intent = new Intent(ViewTabActivity.this,TabActivity.class);
                    startActivity(intent);
                    ViewTabActivity.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
