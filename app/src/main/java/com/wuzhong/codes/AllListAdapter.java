package com.wuzhong.codes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class AllListAdapter extends BaseAdapter {
    private List<String> titleList;
    private Context mContext;

    AllListAdapter(Context context, List<String> array) {
        mContext = context;
        titleList = array;
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TextView texts;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.all_list_item, null);
            texts = (TextView) view.findViewById(R.id.the_text_item);
            texts.setText(titleList.get(position));
        } else {
            view = convertView;
            texts = (TextView) view.findViewById(R.id.the_text_item);
            texts.setText(titleList.get(position));
        }


        return view;
    }
}
