package com.wuzhong.codes.progressbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wuzhong.codes.R;

import java.util.Random;

/**
 * 进度条的需求
 * 1，向左、向右、向两边、向中间的进度条，垂直进度条
 * 2，开头颜色和末尾颜色不一样的渐变进度条
 * 3，开始时整体颜色和结束时整体颜色不一样的进度条。
 * 4，seekbar、多形状的进度条。中心向外扩展、外部向内部包围的进度。
 * 5，圆形进度条、奇形怪状的进度条。表盘状进度条。
 *
 * Created by Wuzhong on 2017-11-7.
 */

public class ProDefActivity extends AppCompatActivity {
    private ProgressBar mProA;
    private int proNum = 0,proNm = 0;
    private TextView mText;
    private ImageView mImagePro,mClockPro,mImageC;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pro_main);
        initView();
        mHandler.postDelayed(mRun,100);


    }

    private Handler mHandler = new Handler();

    private NewUHandler mRun = new NewUHandler();

    private class NewUHandler implements Runnable{

        @Override
        public void run() {
            //这里更新控件
            if (proNum>100){
                proNum = 0;
            }else {
                proNum++;
            }
            mImagePro.getDrawable().setLevel(proNum*100);
            mText.setText("ProgressBar 进度 ： "+proNum);
            mProA.setProgress(proNum);
            //竖进度条
            mImageC.getDrawable().setLevel(proNum*100);

            //----从右向左---
            Random mR = new Random();
            int ranNm;
            if (proNm>5000){
                ranNm = mR.nextInt(30);
            }else{
                ranNm = mR.nextInt(150);
            }

            if (proNm>10000){
                proNm = 0;
            }else {
                proNm += ranNm;
            }
            mClockPro.getDrawable().setLevel(proNm);
//            ---------------------------------

            mHandler.postDelayed(mRun,30);
        }
    }



    private void initView() {
        mProA = (ProgressBar) findViewById(R.id.pro_heng);
        mText = (TextView) findViewById(R.id.pro_text);
        mImagePro = (ImageView) findViewById(R.id.iv_pro_a);
        mClockPro = (ImageView) findViewById(R.id.iv_pro_b);
        mImageC = (ImageView) findViewById(R.id.iv_pro_c);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRun);
    }
}
