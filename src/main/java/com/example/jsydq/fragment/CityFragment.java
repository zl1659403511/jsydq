package com.example.jsydq.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsydq.R;
import com.example.jsydq.element.CGdElement;
import com.example.jsydq.element.CGridElement;
import com.example.jsydq.element.CVpElement;
import com.example.jsydq.element.CZhanElement;
import com.example.jsydq.element.CZhudaElement;
import com.example.jsydq.entity.City;
import com.example.jsydq.request.BaseRequest;
import com.example.jsydq.request.CityRequest;
import com.example.jsydq.utils.Constants;
import com.example.jsydq.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Lionheart on 2016/6/3.
 */
public class CityFragment extends BaseFragment {
    @ViewInject(R.id.fl_vp)
    FrameLayout fl_vp;
    @ViewInject(R.id.fl_gv_icon)
    FrameLayout fl_gv_icon;
    @ViewInject(R.id.fl_zhuda)
    FrameLayout fl_zhuda;
    @ViewInject(R.id.fl_zhan)
    FrameLayout fl_zhan;
    @ViewInject(R.id.fl_gd)
    FrameLayout fl_gd;
    CityRequest request;
    String url = "city.shtml";
    City cityData;
    CVpElement cVpElement;
    CGridElement cGridElement;
    CZhudaElement cZhudaElement;
    CGdElement cGdElement;
    CZhanElement cZhanElement;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.ERROR:
                    srl.setRefreshing(false);
                    break;
                case Constants.OK:
                    fillAllElements();
                    srl.setRefreshing(false);
                    break;
            }
        }
    };
    @ViewInject(R.id.srl)
    SwipeRefreshLayout srl;
    @Override
    public void initView() {
        view = View.inflate(UIUtils.getContext(),R.layout.f_city,null);
        ViewUtils.inject(this,view);
        request = new CityRequest();
        request.setInter(new BaseRequest.BaseInter<City>() {
            @Override
            public void handleData(City data) {
                cityData = data;
                Message msg = Message.obtain();
                if(cityData ==null){
                    msg.what = Constants.ERROR;
                }else{
                    msg.what = Constants.OK;
                }
                handler.sendMessage(msg);
            }
        });
        cVpElement = new CVpElement();
        fl_vp.addView(cVpElement.getView());
        cGridElement = new CGridElement();
        fl_gv_icon.addView(cGridElement.getView());
        cZhudaElement = new CZhudaElement();
        fl_zhuda.addView(cZhudaElement.getView());
        cGdElement = new CGdElement();
        fl_gd.addView(cGdElement.getView());
        cZhanElement = new CZhanElement();
        fl_zhan.addView(cZhanElement.getView());
        srl.setColorSchemeResources(R.color.red,
                R.color.green,
                R.color.blue);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(){
                    @Override
                    public void run() {
                        //刷新：其余都不动，只是（设置缓存），重新填充数据
                        reloadData();
                    }
                }.start();
            }
        });
    }

    @Override
    public void loadData() {
        request.firstLoad(url);
    }

    @Override
    public void reloadData() {
        request.refreshLoad(url);
    }

    private void fillAllElements() {
        cVpElement.loadData(cityData.getLunbos());
        cVpElement.fillView();
        cGridElement.loadData(null);
        cGridElement.fillView();
        cZhudaElement.loadData(cityData.getZhudas());
        cZhudaElement.fillView();
        cGdElement.loadData(cityData.getGudians());
        cGdElement.fillView();
        cZhanElement.loadData(cityData.getZhanzhengs());
        cZhanElement.fillView();
    }
}
