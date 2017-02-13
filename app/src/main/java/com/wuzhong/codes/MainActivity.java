package com.wuzhong.codes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView theAllListView;
    private List<String> titleList;//标题集合
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        //标题集设置
        setTitleList();
        //找到总列表并给配个适配器和点击事件
        theAllListView = (ListView) findViewById(R.id.the_all_list);
        AllListAdapter adapter = new AllListAdapter(mContext,titleList);
        theAllListView.setAdapter(adapter);
        theAllListView.setOnItemClickListener(this);

    }

    private void setTitleList() {
        titleList = new ArrayList<>();
        titleList.add("标题1");
        titleList.add("标题2");
        titleList.add("标题3");
        titleList.add("标题4");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                break;
            case 1:
                break;

        }
    }
}
