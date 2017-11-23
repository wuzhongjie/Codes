package com.wuzhong.codes.viewtab;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wuzhong.codes.R;


/**
 * 页面工厂
 * Created by Wuzhong on 2017-10-24.
 */

public class TabFactory implements View.OnClickListener {

    private Handler mHandler = new Handler();
    private UpRunable mRunable = new UpRunable();
    private ProgressBar mBar;
    private int mBarNum = 0;
    private Button mBtna;
    private View view;


    private static TabFactory mTab;

    private TabFactory(){}

    public static TabFactory getInstance(){
        if (mTab==null){
            mTab = new TabFactory();
        }
        return mTab;
    }

    public View getViewA(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.tab_item_pro, null);
        mBtna = (Button) view.findViewById(R.id.tab_btn);
        mBtna.setOnClickListener(this);
        mBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mBar.setProgress(0);
        return view;
    }

    private boolean mBarBool = false;//进度条逆向开关

    private class UpRunable implements Runnable {

        @Override
        public void run() {
            if (mBarNum >= 100) {
                mBarBool = true;
            } else if (mBarNum <= 0) {
                mBarBool = false;
            }
            if (mBarBool) {
                mBarNum--;
            } else {
                mBarNum++;
            }
            mBar.setProgress(mBarNum);
            mBar.invalidate();
            mHandler.postDelayed(mRunable, 300);
        }
    }

    public View getViewB(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.tab_item_btn, null);
        mBtna = (Button) view.findViewById(R.id.tab_btn);
        mBtna.setOnClickListener(this);
        return view;
    }

    public View getViewC(LayoutInflater inflater) {
        View view = TabThrPage.getInstance().getView(inflater);
        return view;
    }

    public View getViewD( LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.tab_item_btn, null);
        mBtna = (Button) view.findViewById(R.id.tab_btn);
        mBtna.setOnClickListener(TabThrPage.getInstance());
        return view;
    }

    public View getViewE( LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.tab_item, null);
        TextView text = (TextView) view.findViewById(R.id.tab_text);
        text.setText("第5页");
        return view;
    }


    private boolean startPro = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_btn:
                if (startPro != true) {
                    mHandler.postDelayed(mRunable, 1000);//开启更新线程.
                    startPro = true;
                } else {
                    mHandler.removeCallbacks(mRunable);
                    startPro = false;
                }

                break;
        }
    }
}
