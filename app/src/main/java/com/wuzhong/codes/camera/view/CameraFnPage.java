package com.wuzhong.codes.camera.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wuzhong.codes.R;

/**
 * 家庭动态页面
 * Created by Wuzhong on 2017-11-2.
 */

public class CameraFnPage extends BaseCameraPage implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private Context context;
    private static CameraFnPage mPage;
    private Handler mHandler;
    private TextView mTitleText;
    private ImageButton mStartBtn;
    private SwipeRefreshLayout mSwipe;

     CameraFnPage(Context context) {
        this.context = context;

    }
    public static CameraFnPage getInstance(Context context){
        if (mPage == null){
            mPage = new CameraFnPage(context);
        }
        return mPage;
    }

    @Override
    protected void initView(LayoutInflater inflater, Handler cameraHandler) {
        this.mHandler = cameraHandler;
        mView = inflater.inflate( R.layout.camera_b_page,null);
        mTitleText = (TextView) mView.findViewById(R.id.camera_bar_title_text);
        mTitleText.setText("家庭动态");
        mStartBtn = (ImageButton) mView.findViewById(R.id.start_btn);
        mStartBtn.setOnClickListener(this);
        mSwipe = (SwipeRefreshLayout)mView.findViewById(R.id.start_swipe);
        mSwipe.setOnRefreshListener(this);
        mSwipe.setProgressViewOffset(true,140,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources().getDisplayMetrics()));
        mSwipe.setColorSchemeColors(Color.parseColor("#303f9f"));
        mSwipe.setProgressBackgroundColorSchemeColor(Color.parseColor("#99ffffff"));
    }

    @Override
    protected void refreshView() {

    }

    @Override
    public void onClick(View v) {
        startDialog();
    }

    //弹出Dialog提示用户安装摄像头
    private void startDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("开启动态需要一台摄像机");
        builder.setMessage("摄像机能够帮您智能看家,发现异常立即发送报警消息通知您,还能帮您实时照看家中老人和小孩,记录他们每一个温馨的微笑和可爱的动作.");
        builder.setPositiveButton("去购买", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "买买买", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public SwipeRefreshLayout getRefreshLayout(){
        return mSwipe;
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(2,300);
    }
}
