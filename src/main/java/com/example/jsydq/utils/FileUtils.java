package com.example.jsydq.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Lionheart on 2016/5/4.
 */
public class FileUtils {
    public static final String CACHE = "cache";
    public static final String ICON = "icon";
    public static final String ROOT = "JSYDQ";
    public static File getDir(String str){
        StringBuilder path = new StringBuilder();
        if(isSDAvail()){
            path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            path.append(File.separator);
            path.append(ROOT);
            path.append(File.separator);
            path.append(str);
        }else{
            //写到/data/data
            File cacheDir = UIUtils.getContext().getCacheDir();
            path.append(cacheDir.getAbsolutePath());
        }
        File file = new File(path.toString());
        if(!file.exists() || !file.isDirectory()){
            file.mkdirs();
        }
        return file;
    }
    private static boolean isSDAvail() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }
    public static File getCacheDir(){
        return getDir(CACHE);
    }
    public static File getIconDir(){
        return getDir(ICON);
    }
}