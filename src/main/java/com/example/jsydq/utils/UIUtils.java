package com.example.jsydq.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Lionheart on 2016/5/3.
 */
public class UIUtils {
    public static String[] getStringArray(int resName){
        String[] arrays = getResources().getStringArray(resName);
        return arrays;
    }
    public static Resources getResources(){
        return BaseApplication.getApplication().getResources();
    }

    /** dip转换px */
    public static int dip2px(int dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
    /** pxz转换dip */
    public static int px2dip(int px) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
    public static Context getContext(){
        return BaseApplication.getApplication();
    }
    public static void runOnUiThread(Runnable runnable) {
        //如果主线程
        if(BaseApplication.getMainId() == android.os.Process.myTid()){
            runnable.run();
        }else{
            //如果子线程
            //将runnable提交到主线程运行
            BaseApplication.getHandler().post(runnable);
        }
    }
}
