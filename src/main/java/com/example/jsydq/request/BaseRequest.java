package com.example.jsydq.request;

import android.util.Log;

import com.example.jsydq.utils.Constants;
import com.example.jsydq.utils.FileUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringWriter;

/**
 * Created by Lionheart on 2016/5/22.
 */
//只试用于get请求
public abstract class BaseRequest<T> {
    public interface BaseInter<T>{
        void handleData(T data);
    }
    public BaseInter dataInter;
    public void setInter(BaseInter dataInter){
        this.dataInter = dataInter;
    }
    public abstract T parseJson(String json);

    public void loadServer(final String getUrl) {
        //获取到json并且保存到本地
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, Constants.SERVER+getUrl , new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                saveLocal(result, getUrl);
                T data = parseJson(result);
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

    public void saveLocal(String json,String getUrl){
        File dir = FileUtils.getCacheDir();
        BufferedWriter bw = null;
        try {
            File file = new File(dir,getUrl);
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(json);
            bw.flush();
            bw.close();
            Log.i("TAG","缓存写入");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(bw);
        }
    }

    public String loadLocal(String getUrl){
        File dir = FileUtils.getCacheDir();
        File file = new File(dir,getUrl);
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String str = null;
            StringWriter sw = new StringWriter();
            while((str=br.readLine())!=null){
                //写入内存
                sw.write(str);
            }
            //得到json
            Log.i("TAG", "读取缓存");
            return sw.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //第一次加载
    public void firstLoad(String getUrl){
        String json = loadLocal(getUrl);
        if(json == null){
            //加载，缓存，并且解析
            loadServer(getUrl);
        }else{
            T data = parseJson(json);
            if(null!=dataInter){
                dataInter.handleData(data);
            }
        }
    }

    //刷新加载
    public void refreshLoad(String getUrl){
        loadServer(getUrl);
    }
}
