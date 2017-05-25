package com.example.jsydq.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.jsydq.utils.BitmapHelper;
import com.example.jsydq.utils.UIUtils;
import com.example.jsydq.utils.ViewUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Lionheart on 2016/6/2.
 */
public abstract class BaseFragment extends Fragment{
    public FrameLayout fl;
    public View view;
    public BitmapUtils bitmapUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bitmapUtils = BitmapHelper.getBitmapUtils();
        if(fl==null){
            fl = new FrameLayout(UIUtils.getContext());
            initView();
            fl.addView(view);
        }else{
            ViewUtils.remove(fl);
        }
        return fl;
    }
    public abstract void initView();
    public abstract void loadData();
    public abstract void reloadData();
}
