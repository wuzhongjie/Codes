package com.wuzhong.codes.camera.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wuzhong.codes.R;

import java.util.Random;


/**
 * 摄像头页面
 * Created by Wuzhong on 2017-10-30.
 */

public class CameraPage extends BaseCameraPage implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private static CameraPage mPage;

    private TextView mTitleText;
    private ImageButton mTitleAdd,mEye,mSet,mVic,mPlb,mPlay;
    private RelativeLayout mImageScreen,mCrouton;
    private Context context;
    private SwipeRefreshLayout mRefreshLayout;
    private Handler camHandler;

    private CameraPage(Context context) {
        this.context = context;
    }

    @Override
    protected void refreshView() {

    }

    @Override
    protected void initView(LayoutInflater inflater, Handler mainHandler) {
        this.camHandler = mainHandler;
        mView = inflater.inflate(R.layout.camera_a_page,null);
        mTitleAdd = (ImageButton) mView.findViewById(R.id.camera_titlebar_add);

        mEye = (ImageButton) mView.findViewById(R.id.camera_imgbtn_eye);
        mVic = (ImageButton) mView.findViewById(R.id.camera_imgbtn_vic);
        mPlb = (ImageButton) mView.findViewById(R.id.camera_imgbtn_bac);
        mSet = (ImageButton) mView.findViewById(R.id.camera_imgbtn_set);

        mPlay = (ImageButton) mView.findViewById(R.id.camera_screen_play);

        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.camera_swipe);
        mRefreshLayout.setOnRefreshListener(this);

        mPlay.setOnClickListener(this);

        mEye.setOnClickListener(this);
        mVic.setOnClickListener(this);
        mPlb.setOnClickListener(this);
        mSet.setOnClickListener(this);


        mImageScreen = (RelativeLayout) mView.findViewById(R.id.relative_screen);
        mImageScreen.setOnClickListener(this);

        mCrouton = (RelativeLayout) mView.findViewById(R.id.crouton_relative);

        mTitleAdd.setVisibility(View.VISIBLE);
        mTitleText = (TextView) mView.findViewById(R.id.camera_bar_title_text);

        mTitleText.setText("我的摄像机");


    }

    public static CameraPage getInstance(Context context){
        if (mPage == null){
            mPage = new CameraPage(context);
        }
        return mPage;
    }

    public SwipeRefreshLayout getRefreshLayout(){
        return mRefreshLayout;
    }

    public RelativeLayout getRelativeLayout(){
        return mCrouton;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context,"无法使用此功能",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        //刷新的时候通知activity来重新获取一下页面.
        Log.e("TAG","onRefresh");
        camHandler.sendEmptyMessageDelayed(1,1000);
    }









}
