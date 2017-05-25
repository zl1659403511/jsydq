package com.example.jsydq.fragment;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsydq.R;
import com.example.jsydq.entity.Grid;
import com.example.jsydq.entity.Novel;
import com.example.jsydq.request.BaseRequest;
import com.example.jsydq.request.ShelfRequest;
import com.example.jsydq.utils.Constants;
import com.example.jsydq.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lionheart on 2016/6/3.
 */
public class ShelfFragment extends BaseFragment{

    public static List<Novel> novels = new ArrayList<>();
    public ShelfRequest request = new ShelfRequest();
    public String url = "shelf.shtml";
    private GridView gv;
    private SwipeRefreshLayout srl;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constants.OK:
                    //刷新UI
                    gv.setAdapter(new BookAdapter());
                    srl.setRefreshing(false);
                    break;

                case Constants.ERROR:
                    srl.setRefreshing(false);
                    break;
            }
        }
    };
    @Override
    public void initView() {
        view = View.inflate(UIUtils.getContext(), R.layout.f_shelf,null);
        gv = (GridView) view.findViewById(R.id.gv);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        request.setInter(new BaseRequest.BaseInter<List<Novel>>() {
            @Override
            public void handleData(List<Novel> data) {
                Message msg = Message.obtain();
                if(data==null){
                    //处理
                    msg.what = Constants.ERROR;
                }else{
                    //处理
                    msg.what = Constants.OK;
                    novels = data;
                }
                handler.sendMessage(msg);
            }
        });
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        reloadData();
                    }
                }.start();
            }
        });
        srl.setColorSchemeResources(R.color.red,
                R.color.green,
                R.color.blue);
    }
    @Override
    public void loadData() {
        request.firstLoad(url);
    }
    @Override
    public void reloadData() {
        request.refreshLoad(url);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.loadData();
    }


    class BookAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return novels.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_book, null);
            }
            ImageView ivImageView = (ImageView) convertView.findViewById(R.id.iv_book_cover);
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            TextView tvAuthor = (TextView) convertView.findViewById(R.id.tv_author);
            String imgPath = novels.get(position).getImgPath();
            if (ivImageView != null) {
                bitmapUtils.display(ivImageView,imgPath);
            }
            tvTitle.setText(novels.get(position).getName());
            tvAuthor.setText(novels.get(position).getAuthor());
            return convertView;
        }
    }
}
