package com.example.jsydq.request;

import android.util.Log;

import com.example.jsydq.entity.Chapter;
import com.example.jsydq.utils.Constants;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by Lionheart on 2016/6/13.
 */
public class BookRequest extends BaseRequest<Chapter>{
    @Override
    public Chapter parseJson(String json) {
        Gson gson = new Gson();
        Chapter chapter = gson.fromJson(json, Chapter.class);
        return chapter;
    }
    public void loadServer(final String getUrl) {
        //获取到json并且保存到本地
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, Constants.CONTENT_ADDR+getUrl , new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                saveLocal(result, getUrl);
                Chapter data = parseJson(result);
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

    //属于哪一篇小说，这个小说的那一章
    public Chapter loadLocalAndParseJson(int novelId,int cIndex){
        //n2_1.json
        String cacheDir = "n"+novelId+"_"+cIndex+".json";
        String json = loadLocal(cacheDir);
        Chapter chapter = parseJson(json);
        return chapter;
    }



}