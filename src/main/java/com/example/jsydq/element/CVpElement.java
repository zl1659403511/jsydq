package com.example.jsydq.element;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jsydq.R;
import com.example.jsydq.entity.Lunbo;
import com.example.jsydq.utils.UIUtils;

import java.util.List;

/**
 * Created by Lionheart on 2016/6/6.
 */
public class CVpElement extends BaseElement<List<Lunbo>>{
    ViewPager vp;
    @Override
    public void initView() {
        view = View.inflate(UIUtils.getContext(), R.layout.e_lunbo_vp,null);
        vp = (ViewPager) view.findViewById(R.id.e_vp);
    }
    @Override
    public void fillView() {
        vp.setAdapter(new CVpAdapter());
        lunbo();
    }

    class CVpAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 10000;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(UIUtils.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            bu.display(iv,data.get(position%data.size()).getImgPath());
            container.addView(iv);
            return iv;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    Handler handler;
    private void lunbo() {
        if(null==handler){
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    int currentItem = vp.getCurrentItem();
                    currentItem++;
                    vp.setCurrentItem(currentItem);
                    handler.sendMessageDelayed(Message.obtain(), 2000);
                }
            };
            handler.sendMessageDelayed(Message.obtain(),2000);
        }
    }
}
