package com.example.jsydq.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsydq.R;
import com.example.jsydq.utils.Constants;
import com.example.jsydq.utils.UIUtils;

/**
 * Created by Lionheart on 2016/6/3.
 */
public class FindFragment extends BaseFragment{

    private WebView wv;
    private SwipeRefreshLayout srl;

    @Override
    public void initView() {
        view = View.inflate(UIUtils.getContext(), R.layout.f_find,null);
        wv = (WebView)view.findViewById(R.id.wv);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        wv.setVerticalScrollBarEnabled(false);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        srl.setColorSchemeResources(R.color.red,
                R.color.green,
                R.color.blue);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    public void run() {
                        wv.loadUrl(Constants.SERVER + "find.shtml");
                    }
                }.start();
            }
        });
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                srl.setRefreshing(false);
            }
        });

    }

    @Override
    public void loadData() {
        wv.loadUrl(Constants.SERVER+"find.shtml");
    }

    @Override
    public void reloadData() {

    }
}
