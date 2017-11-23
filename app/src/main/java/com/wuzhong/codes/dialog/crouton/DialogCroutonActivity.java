package com.wuzhong.codes.dialog.crouton;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.wuzhong.codes.R;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Crouton滑出菜单栏
 * Created by Wuzhong on 2017-10-27.
 */

public class DialogCroutonActivity extends AppCompatActivity {
    private Button mBtnA;
    private Button mBtnB;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_crouton_main);

        init();
    }

    private void init() {
        mBtnA = (Button) findViewById(R.id.dialog_btn_a);
        mBtnB = (Button) findViewById(R.id.dialog_btn_b);
        mBtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crouton.clearCroutonsForActivity(DialogCroutonActivity.this);
                Style aStyle = Style.ALERT;
                Crouton.makeText(DialogCroutonActivity.this,"顶部滑出提示",aStyle).show();
            }
        });
        mBtnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crouton.clearCroutonsForActivity(DialogCroutonActivity.this);
                Style bStyle = Style.INFO;
                Crouton.makeText(DialogCroutonActivity.this,"随随便便就滑出的提示",bStyle,R.id.dialog_main_linearlayout).show();
            }
        });




    }
}
