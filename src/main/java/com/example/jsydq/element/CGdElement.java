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
public class CGdElement extends BaseElement<List<Novel>>{
    SListView lv;
    @Override
    public void initView() {
        view = View.inflate(UIUtils.getContext(), R.layout.e_gd,null);
        lv = (SListView) view.findViewById(R.id.lv);
    }
    @Override
    public void fillView() {
        lv.setAdapter(new GDAdapter());
    }

    private final int NORMAL_TYPE = 0;
    private final int FIRST_TYPE = 1;
    class GDAdapter extends BaseAdapter{
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
            GDHolder gdHolder=null;
            ZhudaHolder zhudaHolder=null;
            Novel novel = data.get(position);
            switch (getItemViewType(position)){
                case NORMAL_TYPE:
                    if(convertView ==null){
                        convertView = View.inflate(UIUtils.getContext(),R.layout.item_gd,null);
                        gdHolder = new GDHolder();
                        gdHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                        gdHolder.tvAuthor = (TextView) convertView.findViewById(R.id.tv_author);
                        gdHolder.tvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
                        convertView.setTag(gdHolder);
                    }else{
                        gdHolder = (GDHolder) convertView.getTag();
                    }
                    gdHolder.tvTitle.setText(novel.getName());
                    gdHolder.tvAuthor.setText(novel.getAuthor());
                    gdHolder.tvDesc.setText(novel.getDesc());
                    break;
                case FIRST_TYPE:
                    if(convertView==null){
                        convertView = View.inflate(UIUtils.getContext(),R.layout.item_zhuda,null);
                        zhudaHolder = new ZhudaHolder();
                        zhudaHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                        zhudaHolder.tvAuthor = (TextView) convertView.findViewById(R.id.tv_author);
                        zhudaHolder.tvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
                        zhudaHolder.iv = (ImageView) convertView.findViewById(R.id.iv);
                        convertView.setTag(zhudaHolder);
                    }else{
                        zhudaHolder = (ZhudaHolder)convertView.getTag();
                    }
                    bu.display(zhudaHolder.iv,novel.getImgPath());
                    zhudaHolder.tvTitle.setText(novel.getName());
                    zhudaHolder.tvAuthor.setText(novel.getAuthor());
                    zhudaHolder.tvDesc.setText(novel.getDesc());
                    break;
            }
            return convertView;
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;
        }

        @Override
        public int getItemViewType(int position) {
            if(position==0){
                return FIRST_TYPE;
            }else{
                return NORMAL_TYPE;
            }
        }
    }

    static class GDHolder{
        static TextView tvTitle,tvAuthor,tvDesc;
    }
    static class ZhudaHolder{
        static ImageView iv ;
        static TextView tvTitle,tvAuthor,tvDesc;
    }

}

