package com.wuzhong.codes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通列表activity,根据传入的参数来决定显示内容和跳转.
 * Created by Wuzhong on 2017-10-27.
 */

public class BaseListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView theAllListView;
    private List<String> titleList;//标题集合
    private Context mContext = this;
    private ArrayList<String> nameStrings;
    private ArrayList<String> className;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        className = getIntent().getStringArrayListExtra("ClassName");
        nameStrings = getIntent().getStringArrayListExtra("NameList");

        setTitleList();
        theAllListView = (ListView) findViewById(R.id.the_all_list);
        AllListAdapter adapter = new AllListAdapter(mContext, titleList);
        theAllListView.setAdapter(adapter);
        theAllListView.setOnItemClickListener(this);
    }

    private void setTitleList() {
        titleList = new ArrayList<>();
//        titleList.add("SwipeRefreshLayout系统默认下拉刷新");//read

        for (int j = 0; j < nameStrings.size(); j++) {
            titleList.add(nameStrings.get(j));
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Class clazz = Class.forName(className.get(position));
            Intent intent = new Intent(BaseListActivity.this, clazz);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
