package com.example.jsydq.request;

import com.example.jsydq.entity.City;
import com.google.gson.Gson;

/**
 * Created by Lionheart on 2016/6/6.
 */
public class CityRequest extends BaseRequest<City>{
    @Override
    public City parseJson(String json) {
        Gson gson = new Gson();
        City city = gson.fromJson(json, City.class);
        return city;
    }
}
