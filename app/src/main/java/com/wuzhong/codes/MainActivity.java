package com.wuzhong.codes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wuzhong.codes.camera.CameraSplashActivity;
import com.wuzhong.codes.maincraft.MainCraft;
import com.wuzhong.codes.read.ReadActivity;
import com.wuzhong.codes.utils.IDUtil;
import com.wuzhong.codes.utils.LTUtil;
import com.wuzhong.codes.viewtab.ViewTabActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView theAllListView;
    private List<String> titleList;//标题集合
    private Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mContext = this;
        //标题集设置
        setTitleList();
        //找到总列表并给配个适配器和点击事件
        theAllListView = (ListView) findViewById(R.id.the_all_list);
        AllListAdapter adapter = new AllListAdapter(mContext, titleList);
        theAllListView.setAdapter(adapter);
        theAllListView.setOnItemClickListener(this);
    }

    /*标题组*/
    private void setTitleList() {
        titleList = new ArrayList<>();
        titleList.add("语音合成");//read -> 0
        titleList.add("使用ViewPager带自动更新的Tab");//ViewTab -> 1
        titleList.add("MainCraft图形计算");//MainCraft -> 2
        titleList.add("ListView案例");//listview -> 3
        titleList.add("仿制360摄像头");//camera -> 4
        titleList.add("Snackbar/dialog/toast提示框");//dialog -> 5
        titleList.add("progressbar");
        titleList.add("Splash");
        titleList.add("Button");
        titleList.add("TextView");
        titleList.add("其他自定义控件");
        titleList.add("二维码扫描");
        titleList.add("百度地图SDK");
        titleList.add("验证码发送");
        titleList.add("数据库操作");
        titleList.add("即时通讯");
        titleList.add("播放器界面");
        titleList.add("模仿其他app界面");
        titleList.add("网络请求");
        titleList.add("PopWindow");
        titleList.add("widget桌面控件");
        titleList.add("coodinatorlayout与滚动处理");
        titleList.add("RecycleView");


    }

    /* 点击事件 */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(this, ReadActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent.setClass(this, ViewTabActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent.setClass(this, MainCraft.class);
                startActivity(intent);
                break;
            case 3:
                LTUtil.start(this,intent,IDUtil.LIST_LISTVIEW);
                break;
            case 4:
                intent = new Intent(this, CameraSplashActivity.class);
                startActivity(intent);
                break;
            case 5:
                LTUtil.start(this,intent,IDUtil.LIST_DIALOG);
                break;
            case 6:
                LTUtil.start(this,intent,IDUtil.LIST_PROGRESSBAR);
                break;

        }
    }





}
