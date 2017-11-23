package com.wuzhong.codes.listview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wuzhong.codes.R;

import java.util.ArrayList;

/**
 * Created by Wuzhong on 2017-10-26.
 */

public class LvAActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ArrayList mDatas;

    private ListView mList;
    private ArrayAdapter mAdapt;
    private SwipeRefreshLayout mSwipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mDatas = new ArrayList();
        mDatas.add("SwipeRefreshLayout");
        mDatas.add("官方下拉刷新控件");
        mDatas.add("V4包");
        mList = (ListView) findViewById(R.id.the_all_list);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mAdapt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mDatas);
        mList.setAdapter(mAdapt);
        mSwipe.setOnRefreshListener(this);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    mDatas.add("将ListView等需要刷新的控件放入此控件");
                    mDatas.add("实现OnRefreshListener接口的onRefresh方法");
                    mAdapt.notifyDataSetChanged();
                    if (mSwipe.isRefreshing()) {
                        mSwipe.setRefreshing(false);
                    }
                    break;

            }
        }
    };

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(1,1200);
    }
}
