package com.example.jsydq.entity;

/**
 * Created by Lionheart on 2016/5/18.
 */
public class Chapter {
    private String title;
    private String content;
    private int cIndex;
    private int novelId;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getcIndex() {
        return cIndex;
    }
    public void setcIndex(int cIndex) {
        this.cIndex = cIndex;
    }
    public int getNovelId() {
        return novelId;
    }
    public void setNovelId(int novelId) {
        this.novelId = novelId;
    }
}
