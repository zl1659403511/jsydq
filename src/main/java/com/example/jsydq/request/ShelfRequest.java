package com.example.jsydq.request;

import com.example.jsydq.entity.Novel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Lionheart on 2016/6/3.
 */
public class ShelfRequest extends BaseRequest<List<Novel>>{
    @Override
    public List<Novel> parseJson(String json) {
        Gson gson = new Gson();
        List<Novel> datas = gson.fromJson(json, new TypeToken<List<Novel>>() {
        }.getType());
        return datas;
    }
}
