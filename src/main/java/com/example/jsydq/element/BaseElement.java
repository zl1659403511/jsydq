package com.example.jsydq.element;

import android.view.View;

import com.example.jsydq.utils.BitmapHelper;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Lionheart on 2016/6/6.
 */
public abstract class BaseElement<T> {
    public View view;
    public T data;
    public BitmapUtils bu;
    public BaseElement(){
        initView();
        bu = BitmapHelper.getBitmapUtils();
    }
    public abstract void initView();
    public View getView(){
        return this.view;
    }
    public void loadData(T data){
        this.data = data;
    }
    public abstract void fillView();
}
