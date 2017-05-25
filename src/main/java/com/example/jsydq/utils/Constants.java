package com.example.jsydq.utils;


import com.example.jsydq.entity.Chapter;

/**
 * Created by Lionheart on 2016/5/22.
 */
public class Constants {
    public static final String SERVER = "http://10.0.3.2:9000/read/";
    public static final String IMAGE_ADDR = SERVER+"source_images/";
    public static final String CONTENT_ADDR = "http://10.0.3.2:9000/read/content/";
    public static final int OK = 1;
    public static final int ERROR = 2;


    //小说的主要信息
    //ID
    public static int novelId = 1;
    //当前是第几章
    public static int cIndex = 1;
    //一共多少章
    public static int chapterCount = 1;
    //当前章节的小说内容
    public static Chapter curChapter = null;
}
