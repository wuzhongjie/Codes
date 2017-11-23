package com.wuzhong.codes.maincraft;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wuzhong.codes.R;

/**
 * minecraft圆形计算
 * Created by Wuzhong on 2017-3-23.
 */

public class MainCraft extends AppCompatActivity implements View.OnClickListener {
    private EditText mEdit;
    private TextView mText;
    private Button mButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.craft_main);
        initView();
    }

    private void initView() {
        mEdit = (EditText) findViewById(R.id.maincraft_edit);
        mText = (TextView) findViewById(R.id.maincraft_text);
        mButton = (Button) findViewById(R.id.maincraft_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.maincraft_button:
                Message msg = Message.obtain();
                msg.what = 1;
                mHandler.sendMessage(msg);
                break;
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //计算并显示结果.

            String cont = mEdit.getText().toString();
            if (cont.matches("^[0-9]*[1-9][0-9]*$")){
                int count = Integer.parseInt(cont);
                if (count<=999){
                    String text = "[";
                    double x = 0.5;
                    double y = count - 0.5;
                    int num = 0;
                    while (x <= y) {
                        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
                        boolean jieguo = count >= r;
                        if (jieguo) {
                            num++;
                        } else {
                            y--;
                            if (text.equals("[")) {
                                text += num;
                            } else {
                                text += "," + num;
                            }
                            num = 1;
                        }
                        x += 1;
                    }
                    text += "]";
                    mText.setText(text);
                }
            }
        }
    };
}
