package com.example.jsydq.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by Lionheart on 2016/5/4.
 */
public class ViewUtils {
    public static void remove(View v){
        ViewParent parent = v.getParent();
        if(parent instanceof ViewGroup){
            ((ViewGroup) parent).removeView(v);
        }
    }
}
