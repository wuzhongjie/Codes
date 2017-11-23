package com.wuzhong.codes.progressbar.round;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.wuzhong.codes.R;


/**
 * Wuzhong 于 2017-11-17 创建.
 */

public class ProRActivity extends AppCompatActivity {

    private CircleView mCircleProgress;
    private DialProgress mDialProgress;
    private WaveProgress mWaveProgress;
    private SeekBar mSeekBar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pro_round);
        mCircleProgress = (CircleView) findViewById(R.id.circle_progress_bar1);
        mCircleProgress.setValue(0);
        mCircleProgress.setMaxValue(10000);
        mDialProgress = (DialProgress) findViewById(R.id.dial_progress_bar);
        mWaveProgress = (WaveProgress) findViewById(R.id.wave_progress_bar);
        mDialProgress.setValue(0);
        mWaveProgress.setValue(0);
        mSeekBar = (SeekBar) findViewById(R.id.pro_seek_bar);
        mDialProgress.setMaxValue(10000);
        mWaveProgress.setMaxValue(10000);
        mSeekBar.setProgress(0);
        mSeekBar.setMax(10000);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //滑块改变的时候
                mCircleProgress.setValue(progress);
                mDialProgress.setValue(progress);
                mWaveProgress.setValue(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
