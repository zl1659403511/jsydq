package com.example.jsydq.request;

import android.util.Log;

import com.example.jsydq.entity.ChapterSum;
import com.example.jsydq.utils.Constants;
import com.example.jsydq.utils.FileUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;

/**
 * Created by Lionheart on 2016/6/13.
 */
public class SummaryRequest extends BaseRequest<ChapterSum>{
    @Override
    public ChapterSum parseJson(String json) {
        Gson gson = new Gson();
        ChapterSum chapterSum = gson.fromJson(json, ChapterSum.class);
        return chapterSum;
    }

    @Override
    public void loadServer(final String getUrl) {
        //获取到json并且保存到本地
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, Constants.SERVER+getUrl , new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                ChapterSum data = parseJson(result);
                if (null != dataInter) {
                    Log.i("TAG", "从网络处理回调data");
                    dataInter.handleData(data);
                }
            }
            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("TAG", s);
                if (null != dataInter) {
                    Log.i("TAG", "从网络处理空data");
                    dataInter.handleData(null);
                }
            }
        });
    }

    public String loadLocal(String getUrl){
        return null;
    }

    public void firstLoad(String getUrl){
        loadServer(getUrl);
    }
}
