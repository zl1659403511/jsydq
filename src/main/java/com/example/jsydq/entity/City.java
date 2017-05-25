package com.example.jsydq.entity;

import java.util.List;

/**
 * Created by Lionheart on 2016/5/23.
 */

public class City {
    private List<Lunbo> lunbos;
    private List<Novel> zhudas;
    private List<Novel> gudians;
    private List<Novel> zhanzhengs;
    private List<PriceNovel> zhekous;
    private List<Novel> frees;

    public List<Lunbo> getLunbos() {
        return lunbos;
    }

    public void setLunbos(List<Lunbo> lunbos) {
        this.lunbos = lunbos;
    }

    public List<Novel> getZhudas() {
        return zhudas;
    }

    public void setZhudas(List<Novel> zhudas) {
        this.zhudas = zhudas;
    }

    public List<Novel> getGudians() {
        return gudians;
    }

    public void setGudians(List<Novel> gudians) {
        this.gudians = gudians;
    }

    public List<Novel> getZhanzhengs() {
        return zhanzhengs;
    }

    public void setZhanzhengs(List<Novel> zhanzhengs) {
        this.zhanzhengs = zhanzhengs;
    }

    public List<PriceNovel> getZhekous() {
        return zhekous;
    }

    public void setZhekous(List<PriceNovel> zhekous) {
        this.zhekous = zhekous;
    }

    public List<Novel> getFrees() {
        return frees;
    }

    public void setFrees(List<Novel> frees) {
        this.frees = frees;
    }
}
