package com.wuzhong.codes.dialog.snackbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.wuzhong.codes.R;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * 利用Snackbar实现的顶部和底部通知栏
 * Created by Wuzhong on 2017-10-27.
 */

public class DialogSnackbarActivity extends AppCompatActivity {
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private LinearLayout mll;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_snackbar_main);
        init();
        setOnClick();
    }

    private void setOnClick() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar mSnackbar = Snackbar.make(mButton1,"SnackBar",Snackbar.LENGTH_SHORT);
                mSnackbar.show();

            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crouton.makeText(DialogSnackbarActivity.this,"待实现", Style.INFO).show();
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crouton.makeText(DialogSnackbarActivity.this,"待实现", Style.CONFIRM).show();
            }
        });
    }

    private void init() {
        mButton1 = (Button) findViewById(R.id.snackbar_btn_a);
        mButton2 = (Button) findViewById(R.id.snackbar_btn_b);
        mButton3 = (Button) findViewById(R.id.snackbar_btn_c);
        mll = (LinearLayout) findViewById(R.id.snackbar_ll);

    }

}
