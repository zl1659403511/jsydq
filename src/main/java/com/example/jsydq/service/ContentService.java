package com.example.jsydq.service;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import com.example.jsydq.entity.Chapter;
import com.example.jsydq.request.BookRequest;
import com.example.jsydq.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lionheart on 2016/6/13.
 */
public class ContentService {
    private int height;
    private int width;

    public int normalSize = 32;
    public int titleSize = 64;
    public Paint normalPaint ;
    public Paint titlePaint ;
    public int normalMargin = 15;
    public int graphMargin = 25;

    //当前正在显示的页的第一个字符，并且作为游标累加
    private int currentIndex;
    //当前是第几页
    public int currentPage;
    //这一章节的内容
    private String content;
    private String title;
    //这一章节有多少个字
    private int length;
    //每一页要显示的行
    private List<String> lines = new ArrayList<>();
    private boolean isEnded ;
    private Chapter chapter;

    private Map<Integer,HashMap<Integer,Integer>> startMap = new HashMap();
    private HashMap<Integer,Integer> curChapterMap = new HashMap<>();

    public ContentService(){
        width = 700;
        height = 1200;
        chapter = Constants.curChapter;
        content = chapter.getContent();
        title = chapter.getTitle();
        length = content.length();
        normalPaint = new Paint();
        titlePaint = new Paint();
        normalPaint.setTextSize(normalSize);
        titlePaint.setTextSize(titleSize);
        normalPaint.setColor(Color.BLACK);
        titlePaint.setColor(Color.DKGRAY);
        normalPaint.setTextAlign(Paint.Align.LEFT);
        titlePaint.setTextAlign(Paint.Align.LEFT);
        titlePaint.setTypeface(Typeface.DEFAULT_BOLD);
        currentIndex = 0;
        currentPage = 0;
        curChapterMap.put(0,0);
        startMap.put(Constants.cIndex,curChapterMap);
        initContent();
    }

    //根据每一行的长度，初始化该页的lines
    private void initContent() {
        lines.clear();
        //因为有的页是首页，需要显示题目，当前页能显示内容的高度
        int contentHeight ;
        if(currentPage ==0){
            //这一章的首页
            contentHeight = height - normalMargin-titleSize-normalMargin;
        }else{
            contentHeight = height - normalMargin;
        }
        int perLineMaxCount = width/normalSize;
        while(contentHeight>normalMargin+normalSize && !isEnded){
            //每一行
            String lineContent = "";
            for(int i=0;i<perLineMaxCount;i++){
                currentIndex ++;
                //可能结束的情况
                if(currentIndex>=length){
                    isEnded = true;
                    break;
                }else
                if(content.charAt(currentIndex)=='\n'){
                    lines.add(lineContent);
                    lineContent = "\n";
                    contentHeight-=normalSize;
                    contentHeight-=normalMargin;
                    lines.add(lineContent);
                    currentIndex++;
                    break;
                }else if(content.charAt(currentIndex)==' '){
                    lineContent+=" ";
                }else{
                    lineContent+=content.charAt(currentIndex);
                }
            }
            if(lineContent!="\n"){
                lines.add(lineContent);
                Log.i("TAG",lineContent);
                contentHeight-=normalSize;
                contentHeight-=normalMargin;
            }
        }
    }

    //下一页
    public void nextPage(){
        if(currentIndex>=length){
            //该下一章了
            Constants.cIndex++;
            if(Constants.cIndex>Constants.chapterCount){
                return ;
            }
            BookRequest bookRequest = new BookRequest();
            Chapter data = bookRequest.loadLocalAndParseJson(Constants.novelId, Constants.cIndex);
            chapter = data;
            Constants.curChapter = data;
            title = chapter.getTitle();
            content = chapter.getContent();
            length = content.length();
            currentIndex = 0;
            currentPage = 0;
            curChapterMap = new HashMap<>();
            curChapterMap.put(0,0);
            startMap.put(Constants.cIndex,curChapterMap);
            isEnded = false;
            initContent();
        }else{
            currentIndex++;
            currentPage++;
            curChapterMap.put(currentPage,currentIndex);
            initContent();
        }
    }


    //上一页
    public void prePage(){
        isEnded = false;
        if(currentPage ==0){
            if(Constants.cIndex==1){
                return ;
            }else{
                //上一章
                Constants.cIndex--;
                BookRequest bookRequest = new BookRequest();
                Constants.curChapter = bookRequest.loadLocalAndParseJson(Constants.novelId,Constants.cIndex);
                chapter = Constants.curChapter;
                title = chapter.getTitle();
                content = chapter.getContent();
                length = content.length();
                curChapterMap = startMap.get(Constants.cIndex);
                int maxKey = 0;
                for(Integer key :curChapterMap.keySet()){
                    if(maxKey<key){
                        maxKey = key;
                    }
                }
                currentIndex = curChapterMap.get(maxKey);
                currentPage = maxKey;
                isEnded = false;
                initContent();
            }
        }else{
            currentPage--;
            curChapterMap = startMap.get(Constants.cIndex);
            currentIndex = curChapterMap.get(currentPage);
            initContent();
        }
    }
    //是否是小说的末尾
    public boolean isLastPage(){
        if(currentIndex>=length){

            int index = Constants.cIndex+1;
            if(index>Constants.chapterCount){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    //是否是小说的第一页
    public boolean isFirstPage(){
        if(currentPage==0 && Constants.cIndex==1){
            return true;
        }else{
            return false;
        }
    }

    public List<String> getLines(){
        return lines;
    }

    public String getTitle(){
        return title;
    }

    public float getTitleMargin(){
        float titleWidth = 0;
        for(int i=0;i<title.length();i++){
            titleWidth+=titleSize;
        }
        return width-titleWidth;
    }
}
