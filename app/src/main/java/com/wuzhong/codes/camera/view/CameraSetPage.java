package com.wuzhong.codes.camera.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuzhong.codes.R;

/**
 * Created by Wuzhong on 2017-11-6.
 */

public class CameraSetPage extends BaseCameraPage {

    private static CameraSetPage mPage;

    private Context context;

    private CameraSetPage(Context context) {
        this.context = context;
    }

    @Override
    protected void refreshView() {

    }

    @Override
    protected void initView(LayoutInflater inflater, Handler cameraHandler) {
        mView = inflater.inflate(R.layout.camera_c_page,null);
    }

    public static CameraSetPage getInstance(Context context){
        if (mPage == null){
            mPage = new CameraSetPage(context);
        }
        return mPage;
    }
}
