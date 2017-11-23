package com.wuzhong.codes.utils;

import android.content.Context;
import android.content.Intent;

import com.wuzhong.codes.BaseListActivity;

import java.util.ArrayList;

/**
 * Created by Wuzhong on 2017-11-7.
 */

public class LTUtil {

    private static String packageString = "com.wuzhong.codes";


    /*ListView 标题*/
    private static ArrayList<String> getName(int num) {
        ArrayList nameList = new ArrayList();
        switch (num) {
            case IDUtil.LIST_LISTVIEW:
                nameList.add("SwipeRefreshLayout系统默认下拉刷新");
                break;

            case IDUtil.LIST_DIALOG:
                nameList.add("Crouton滑出型提示框");
                nameList.add("Snackbar系统滑出型提示框");
                break;

            case IDUtil.LIST_PROGRESSBAR:
                nameList.add("一般进度条使用");
                nameList.add("圆形进度条");
                break;

            default:
                nameList.add("没有找到");
                break;
        }
        return nameList;
    }

    /*ListView 类名*/
    private static ArrayList<String> getClass(int num) {
        ArrayList clas = new ArrayList();
        switch (num) {
            case IDUtil.LIST_LISTVIEW:
                clas.add(packageString + ".listview.LvAActivity");
                break;

            case IDUtil.LIST_DIALOG:
                clas.add(packageString + ".dialog.crouton.DialogCroutonActivity");
                clas.add(packageString + ".dialog.snackbar.DialogSnackbarActivity");
                break;

            case IDUtil.LIST_PROGRESSBAR:
                clas.add(packageString + ".progressbar.ProDefActivity");
                clas.add(packageString + ".progressbar.round.ProRActivity");
                break;

            default:
                clas.add(packageString + ".listview.LvAActivity");
                break;
        }
        return clas;
    }


    public static void start(Context packageContext, Intent intent, int id) {
        intent.setClass(packageContext, BaseListActivity.class);
        intent.putStringArrayListExtra("NameList", getName(id));
        intent.putStringArrayListExtra("ClassName", getClass(id));
        packageContext.startActivity(intent);
    }


}
