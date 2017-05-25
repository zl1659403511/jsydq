package com.example.jsydq.utils;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by Lionheart on 2016/5/3.
 */
public class BaseApplication extends Application{
    private static BaseApplication application;
    private static int myTid;
    private static Handler handler;
    @Override
    public void onCreate() {
        //在主线程
        super.onCreate();
        application = this;
        myTid = android.os.Process.myTid();
        handler = new Handler();
    }
    public static Context getApplication(){
        return application;
    }
    public static int getMainId(){
        return myTid;
    }
    public static Handler getHandler(){
        return handler;
    }
}
