package com.wuzhong.codes.camera;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wuzhong.codes.R;
import com.wuzhong.codes.camera.view.CameraFnPage;
import com.wuzhong.codes.camera.view.CameraPage;
import com.wuzhong.codes.camera.view.CameraSetPage;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * 主页
 * Created by Wuzhong on 2017-10-26.
 */

public class CameraMainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mBtna, mBtnb, mBtnc, mBtnd;
    private ImageView mImga, mImgb, mImgc, mImgd;
    private LinearLayout mll;
    private LayoutInflater mlay;
    private View scrView;
    private SwipeRefreshLayout mSwipeCamera, mSwipeStart;
    private RelativeLayout mCrouton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.camera_main);
        initImage();
        refreshImg(1);//默认页
    }

    private void initImage() {
        mll = (LinearLayout) findViewById(R.id.camera_linear);
        mBtna = (LinearLayout) findViewById(R.id.camera_main_btn_a);
        mBtnb = (LinearLayout) findViewById(R.id.camera_main_btn_b);
        mBtnc = (LinearLayout) findViewById(R.id.camera_main_btn_c);
        mBtnd = (LinearLayout) findViewById(R.id.camera_main_btn_d);
        mBtna.setOnClickListener(this);
        mBtnb.setOnClickListener(this);
        mBtnc.setOnClickListener(this);
        mBtnd.setOnClickListener(this);
        mImga = (ImageView) findViewById(R.id.camera_img_a);
        mImgb = (ImageView) findViewById(R.id.camera_img_b);
        mImgc = (ImageView) findViewById(R.id.camera_img_c);
        mImgd = (ImageView) findViewById(R.id.camera_img_d);

    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //刷新第一页
                    if (selected == 1) {
                        refreshImg(selected);
                        Crouton.cancelAllCroutons();
                        Crouton.makeText(CameraMainActivity.this, "您还没有摄像机", Style.INFO, mCrouton).show();
                    }
                    if (mSwipeCamera.isRefreshing()) {
                        mSwipeCamera.setRefreshing(false);
                    }
                    break;
                case 2:
                    if (selected == 2) {
                        refreshImg(selected);
                    }
                    if (mSwipeStart.isRefreshing()) {
                        mSwipeStart.setRefreshing(false);
                    }
                    break;

            }
        }
    };


    private int selected = 1;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_main_btn_a:
                if (selected != 1 ) {
                    mHandler.sendEmptyMessage(1);
                    selected = 1;
                }
                break;
            case R.id.camera_main_btn_b:
                //家庭动态页
                if (selected != 2) {
                    selected = 2;
                    refreshImg(selected);
                }
                break;
            case R.id.camera_main_btn_c:
                //跳转直播页
                if (selected != 3) {
                    selected = 3;
                    refreshImg(selected);
                }
                break;
            case R.id.camera_main_btn_d:
                //设置页面
                if (selected != 4) {
                    selected = 4;
                    refreshImg(selected);
                }
                break;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CameraPage.getInstance(this).removeView();
    }

    //刷新页面状态并将第一页添加到视图中
    private void refreshImg(int id) {
        mImga.setBackgroundResource(R.mipmap.dock_icon_my_camera_normal);
        mImgb.setBackgroundResource(R.mipmap.dock_icon_fn_normal);
        mImgc.setBackgroundResource(R.mipmap.dock_icon_find_normal);
        mImgd.setBackgroundResource(R.mipmap.dock_icon_my_normal);
        mlay = LayoutInflater.from(this);
        switch (id) {
            case 1:
                mImga.setBackgroundResource(R.mipmap.dock_icon_my_camera_selected);
                mImga.invalidate();
                mll.removeAllViews();

                scrView = CameraPage.getInstance(this).getView(mlay, mHandler);
                mSwipeCamera = CameraPage.getInstance(this).getRefreshLayout();
                mCrouton = CameraPage.getInstance(this).getRelativeLayout();
                if (scrView.getLayoutParams()!=null){
                    scrView.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
                    scrView.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                }else{
                    Log.e("TAG","getLayoutParams null");
                }

                mll.addView(scrView);

                mll.invalidate();
                break;
            case 2:
                mImgb.setBackgroundResource(R.mipmap.dock_icon_fn_selected);
                mImgb.invalidate();
                mll.removeAllViews();

                scrView = CameraFnPage.getInstance(this).getView(mlay,mHandler);
                mSwipeStart = CameraFnPage.getInstance(this).getRefreshLayout();

                mll.addView(scrView);
                mll.invalidate();
                break;
            case 3:
                mImgc.setBackgroundResource(R.mipmap.dock_icon_find_selected);
                mImgc.invalidate();



                break;
            case 4:
                mImgd.setBackgroundResource(R.mipmap.dock_icon_my_pressed);
                mImgd.invalidate();

                mll.removeAllViews();
                mll.addView(CameraSetPage.getInstance(this).getView(mlay,mHandler));
                mll.invalidate();
                break;
        }
    }
}
