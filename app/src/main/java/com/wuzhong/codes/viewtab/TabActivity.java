package com.wuzhong.codes.viewtab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.wuzhong.codes.R;

import java.util.ArrayList;

/**
 * tab切换
 * Created by Wuzhong on 2017-10-24.
 */

public class TabActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private ArrayList<View> mViews;
    private LinearLayout mTabA;
    private LinearLayout mTabB;
    private LinearLayout mTabC;
    private LinearLayout mTabD;
    private LinearLayout mTabE;
    private ImageButton mBtnA;
    private ImageButton mBtnB;
    private ImageButton mBtnC;
    private ImageButton mBtnD;
    private ImageButton mBtnE;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tab_act_main);
        initView();
        initPager();
        initEvent();

    }

    private void initView() {
        mPager = (ViewPager) findViewById(R.id.id_viewpage);
        mTabA = (LinearLayout) findViewById(R.id.tab_a);
        mTabB = (LinearLayout) findViewById(R.id.tab_b);
        mTabC = (LinearLayout) findViewById(R.id.tab_c);
        mTabD = (LinearLayout) findViewById(R.id.tab_d);
        mTabE = (LinearLayout) findViewById(R.id.tab_e);
        mBtnA = (ImageButton) findViewById(R.id.img_a);
        mBtnB = (ImageButton) findViewById(R.id.img_b);
        mBtnC = (ImageButton) findViewById(R.id.img_c);
        mBtnD = (ImageButton) findViewById(R.id.img_d);
        mBtnE = (ImageButton) findViewById(R.id.img_e);

        mViews = new ArrayList<>();
        mBtnA.setImageResource(R.mipmap.click);
    }

    private void initPager() {
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        TabFactory mFactory = TabFactory.getInstance();
        View mAview = mFactory.getViewA(mLayoutInflater);
        View mBview = mFactory.getViewB(mLayoutInflater);
        View mCview = mFactory.getViewC(mLayoutInflater);
        View mDview = mFactory.getViewD(mLayoutInflater);
        View mEview = mFactory.getViewE(mLayoutInflater);
        mViews.add(mAview);
        mViews.add(mBview);
        mViews.add(mCview);
        mViews.add(mDview);
        mViews.add(mEview);

        mPagerAdapter = new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        mPager.setAdapter(mPagerAdapter);
    }

    private void initEvent() {
        mTabA.setOnClickListener(this);
        mTabB.setOnClickListener(this);
        mTabC.setOnClickListener(this);
        mTabD.setOnClickListener(this);
        mTabE.setOnClickListener(this);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                int currentItem = mPager.getCurrentItem();
                switch (currentItem) {
                    case 0:
                        reSetImg();
                        mBtnA.setImageResource(R.mipmap.click);
                        break;
                    case 1:
                        reSetImg();
                        mBtnB.setImageResource(R.mipmap.click);
                        break;
                    case 2:
                        reSetImg();
                        mBtnC.setImageResource(R.mipmap.click);
                        break;
                    case 3:
                        reSetImg();
                        mBtnD.setImageResource(R.mipmap.click);
                        break;
                    case 4:
                        reSetImg();
                        mBtnE.setImageResource(R.mipmap.click);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
        });


    }

    private void reSetImg() {
        mBtnA.setImageResource(R.mipmap.unclick);
        mBtnB.setImageResource(R.mipmap.unclick);
        mBtnC.setImageResource(R.mipmap.unclick);
        mBtnD.setImageResource(R.mipmap.unclick);
        mBtnE.setImageResource(R.mipmap.unclick);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_a:
                mPager.setCurrentItem(0);
                reSetImg();
                mBtnA.setImageResource(R.mipmap.click);
                break;
            case R.id.tab_b:
                mPager.setCurrentItem(1);
                reSetImg();
                mBtnB.setImageResource(R.mipmap.click);
                break;
            case R.id.tab_c:
                mPager.setCurrentItem(2);
                reSetImg();
                mBtnC.setImageResource(R.mipmap.click);
                break;
            case R.id.tab_d:
                mPager.setCurrentItem(3);
                reSetImg();
                mBtnD.setImageResource(R.mipmap.click);
                break;
            case R.id.tab_e:
                mPager.setCurrentItem(4);
                reSetImg();
                mBtnE.setImageResource(R.mipmap.click);
                break;
        }
    }
}
