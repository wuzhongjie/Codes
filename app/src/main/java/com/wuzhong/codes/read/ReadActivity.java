package com.wuzhong.codes.read;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.wuzhong.codes.R;
import com.wuzhong.codes.utils.FileUtil;

import java.io.File;

/**
 * 语音合成
 * 将输入的文字朗读出来.
 * 可以设置男女声,音量,速度,音调
 * Created by Wuzhong on 2017-3-13.
 */

public class ReadActivity extends AppCompatActivity implements View.OnClickListener, SpeechSynthesizerListener {
    private LinearLayout mTextLayout;
    private EditText mEdit;
    private Button mPlay, mStop, mPause;

    private static final String SPEECH_FEMALE = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE = "bd_etts_speech_male.dat";
    private static final String SPEECH_TEXT = "bd_etts_text.dat";
    private static final String EN_FEMALE = "bd_etts_speech_female_en.dat";
    private static final String EN_MALE = "bd_etts_speech_male_en.dat";
    private static final String EN_TEXT = "bd_etts_text_en.dat";
    private static final String API_KEY = "LLlXtld9R4fRZPyxtAtLd3H1";
    private static final String APP_ID = "9365525";
    private static final String SECRET_KEY = "5db6802692bc36cb7d876ee5ec024ec5";
    private static final String PRONUNCIATION = "0";//发音人

    private SpeechSynthesizer mSpeechSynthesizer;
    private String mDirPath;
    private static final String DIR_NAME = "TTSModel";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_act_main);
        initEnv();
        initView();
        initTTS();

    }

    private void initEnv() {
        if (mDirPath == null) {
            String sdCardPath = Environment.getExternalStorageDirectory().getPath();
            mDirPath = sdCardPath + "/" + DIR_NAME;
        }
        makeDirs(mDirPath);
        FileUtil.copyAssetsToSdcard(this, false, SPEECH_FEMALE, mDirPath + "/" + SPEECH_FEMALE);
        FileUtil.copyAssetsToSdcard(this, false, SPEECH_MALE, mDirPath + "/" + SPEECH_MALE);
        FileUtil.copyAssetsToSdcard(this, false, SPEECH_TEXT, mDirPath + "/" + SPEECH_TEXT);
        FileUtil.copyAssetsToSdcard(this, false, "english/" + EN_FEMALE, mDirPath + "/" + EN_FEMALE);
        FileUtil.copyAssetsToSdcard(this, false, "english/" + EN_MALE, mDirPath + "/" + EN_MALE);
        FileUtil.copyAssetsToSdcard(this, false, "english/" + EN_TEXT, mDirPath + "/" + EN_TEXT);
    }

    private void makeDirs(String mDirPath) {
        File file = new File(mDirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void initTTS() {
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(this);
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mDirPath + "/"
                + SPEECH_TEXT);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mDirPath + "/" + SPEECH_FEMALE);
        mSpeechSynthesizer.setAppId(APP_ID);
        mSpeechSynthesizer.setApiKey(API_KEY,SECRET_KEY);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER,PRONUNCIATION);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE,SpeechSynthesizer.MIX_MODE_DEFAULT);
        AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);
        if (authInfo.isSuccess()) {
            Log.e("日志","语音合成授权成功");
        } else {
            Log.e("日志","语音合成授权失败");
            String errorMsg = authInfo.getTtsError().getDetailMessage();
        }
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        int result =
                mSpeechSynthesizer.loadEnglishModel(mDirPath + "/" + EN_FEMALE, mDirPath
                        + "/" + EN_FEMALE);
    }

    /* 初始化控件 */
    private void initView() {
        mTextLayout = (LinearLayout) findViewById(R.id.read_text);
        mEdit = (EditText) findViewById(R.id.read_edit);
        mPlay = (Button) findViewById(R.id.read_play);
        mPause = (Button) findViewById(R.id.read_pause);
        mStop = (Button) findViewById(R.id.read_stop);
        mPlay.setOnClickListener(this);
        mPause.setOnClickListener(this);
        mStop.setOnClickListener(this);

    }

    private boolean isPlaying = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.read_play:
                isPlaying = true;
                //将文字朗读出来.
                String toSpeak = mEdit.getText().toString().trim();
                mSpeechSynthesizer.speak(toSpeak);
                Message msg = Message.obtain();
                msg.what = 1;
                mHandler.sendMessage(msg);
                break;
            case R.id.read_pause:
                if (isPlaying){
                    mSpeechSynthesizer.pause();
                }else{
                    mSpeechSynthesizer.resume();
                }
                break;
            case R.id.read_stop:
                mSpeechSynthesizer.stop();
                break;
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                // 改变文字状态
                TextView mConText = new TextView(getApplicationContext());
                mConText.setText(mEdit.getText().toString().trim());
                mConText.setTextSize(24f);
                mConText.setTextColor(Color.BLACK);
                mTextLayout.addView(mConText);
                mEdit.setText("");
            }
        }
    };

    @Override
    public void onSynthesizeStart(String s) {

    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

    }

    @Override
    public void onSynthesizeFinish(String s) {

    }

    @Override
    public void onSpeechStart(String s) {

    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {

    }

    @Override
    public void onSpeechFinish(String s) {

    }

    @Override
    public void onError(String s, SpeechError speechError) {

    }

    @Override
    protected void onDestroy() {
        mSpeechSynthesizer.release();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //使用item.getItemId();获取id
        Intent intent = new Intent(this,ReadSetActivity.class);
        startActivity(intent);
        return true;
    }
}
