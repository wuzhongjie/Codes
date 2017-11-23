package com.wuzhong.codes.viewtab;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.wuzhong.codes.R;

/**
 * Created by Wuzhong on 2017-10-25.
 */

public class TabThrPage implements View.OnClickListener {
    private View view;
    private ProgressBar mBar;
    private Button mBtn;
    private Handler mHandler = new Handler();
    private static TabThrPage mTab;

    private TabThrPage(){}

    public static TabThrPage getInstance(){
        if (mTab==null){
            mTab = new TabThrPage();
        }
        return mTab;
    }

    public View getView(LayoutInflater inflater){
        view = inflater.inflate(R.layout.tab_item_pro,null);
        mBtn = (Button) view.findViewById(R.id.tab_btn);
        mBtn.setOnClickListener(this);
        mBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mBar.setProgress(0);
        return view;
    }

    private boolean startPro = false;

    @Override
    public void onClick(View v) {
        switch(v.getId()){
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

    private int mBarNum = 0;
    private boolean mBarBool = false;
    private RunableTab mRunable = new RunableTab();

    private class RunableTab implements Runnable {

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
}
