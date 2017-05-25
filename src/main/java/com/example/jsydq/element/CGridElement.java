package com.example.jsydq.element;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.jsydq.R;
import com.example.jsydq.entity.Grid;
import com.example.jsydq.utils.UIUtils;
import com.example.jsydq.view.SGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lionheart on 2016/5/23.
 */
public class CGridElement extends BaseElement<List<Grid>>{
    SGridView gv;
    @Override
    public void initView() {
        view = View.inflate(UIUtils.getContext(), R.layout.e_grid,null);
        gv = (SGridView) view.findViewById(R.id.gv);
    }
    @Override
    public void fillView() {
        gv.setAdapter(new GridAdapter());
    }
    @Override
    public void loadData(List<Grid> data1) {
        data = new ArrayList<>();
        data.add(new Grid(R.drawable.g1,"主打"));
        data.add(new Grid(R.drawable.g2,"热门"));
        data.add(new Grid(R.drawable.g3,"排行"));
        data.add(new Grid(R.drawable.g4,"本期"));
        data.add(new Grid(R.drawable.g5,"推荐"));
        data.add(new Grid(R.drawable.g6,"折扣"));
        data.add(new Grid(R.drawable.g7,"免费"));
        data.add(new Grid(R.drawable.g9, "午夜"));
    }

    class GridAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return data.size();
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
            if(null==convertView){
                convertView = View.inflate(UIUtils.getContext(),R.layout.item_icon,null);
            }
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
            TextView tv=  (TextView)convertView.findViewById(R.id.tv);
            Grid grid = data.get(position);
            iv.setImageResource(grid.resource);
            tv.setText(grid.name);
            return convertView;
        }
    }
}


