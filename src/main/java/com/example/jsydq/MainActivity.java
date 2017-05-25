package com.example.jsydq;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jsydq.fragment.BaseFragment;
import com.example.jsydq.fragment.CityFragment;
import com.example.jsydq.fragment.FindFragment;
import com.example.jsydq.fragment.FragmentUtils;
import com.example.jsydq.fragment.ShelfFragment;
import com.example.jsydq.utils.UIUtils;
import com.example.jsydq.view.BookView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Lionheart on 2016/6/2.
 */
public class MainActivity extends FragmentActivity{
    @ViewInject(R.id.rg)
    RadioGroup rg;
    @ViewInject(R.id.fl_menu)
    FrameLayout fl;
    @ViewInject(R.id.vp)
    ViewPager vp;
    @ViewInject(R.id.tv_title)
    TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        rg.check(R.id.rb_1);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_1:
                        vp.setCurrentItem(0);
                        tvTitle.setText("看书");
                        break;
                    case R.id.rb_2:
                        vp.setCurrentItem(1);
                        tvTitle.setText("书城");
                        break;
                    case R.id.rb_3:
                        vp.setCurrentItem(2);
                        tvTitle.setText("发现");
                        break;
                }
            }
        });
        View left = View.inflate(UIUtils.getContext(),R.layout.left_menu,null);
        fl.addView(left);
        vp.setAdapter(new MyAdapter(getSupportFragmentManager()));
        vp.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentUtils.getFragment(position);
                fragment.loadData();
            }
        });
    }


    class MyAdapter extends FragmentPagerAdapter{
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return FragmentUtils.getFragment(position);
        }
        @Override
        public int getCount() {
            return 3;
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        if (null != BookView.instance)
        {
            BookView.instance.startCloseBookAnimation();
        }
    }
}
