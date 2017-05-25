package com.example.jsydq.entity;

import java.util.ArrayList;
import java.util.List;

public class ChapterSum {
	//小说一共多少章
	public int num;
	//每一章所对应的路径
	public List<String> paths = new ArrayList<String>();
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public List<String> getPaths() {
		return paths;
	}
	public void setPaths(List<String> paths) {
		this.paths = paths;
	}
}
