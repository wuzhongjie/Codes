package com.wuzhong.codes.camera.view;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

/**
 *
 * Created by Wuzhong on 2017-11-2.
 */

public abstract class BaseCameraPage {
    public View mView;

    //初始化页面控件
    protected abstract void refreshView();
    public View getView(LayoutInflater inflater, Handler cameraHandler){

        if (mView==null){
            initView(inflater,cameraHandler);
        }
        return mView;
    }

    public void removeView(){
        mView = null;
    }

    protected abstract void initView(LayoutInflater inflater,Handler cameraHandler);


}
