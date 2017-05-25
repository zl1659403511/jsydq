package com.example.jsydq.element;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.jsydq.R;
import com.example.jsydq.entity.Novel;
import com.example.jsydq.utils.UIUtils;
import com.example.jsydq.view.SListView;

import java.util.List;

/**
 * Created by Lionheart on 2016/5/23.
 */
public class CZhudaElement extends BaseElement<List<Novel>>{
    SListView lv;
    @Override
    public void initView() {
        view = View.inflate(UIUtils.getContext(), R.layout.e_zhuda,null);
        lv = (SListView) view.findViewById(R.id.lv);
    }
    @Override
    public void fillView() {
        lv.setAdapter(new ZhudaAdapter());
    }
    class ZhudaAdapter extends BaseAdapter{
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
            Holder holder;
            if(convertView ==null){
                convertView = View.inflate(UIUtils.getContext(),R.layout.item_zhuda,null);
                holder = new Holder();
                holder.iv = (ImageView) convertView.findViewById(R.id.iv);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tvAuthor = (TextView) convertView.findViewById(R.id.tv_author);
                holder.tvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }
            Novel novel = data.get(position);
            bu.display(holder.iv,novel.getImgPath());
            holder.tvTitle.setText(novel.getName());
            holder.tvAuthor.setText(novel.getAuthor());
            holder.tvDesc.setText(novel.getDesc());
            return convertView;
        }
    }

    static class Holder{
        static ImageView iv ;
        static TextView tvTitle,tvAuthor,tvDesc;
    }
}

