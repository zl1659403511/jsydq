package com.example.jsydq.element;

import android.view.View;
import android.widget.ImageView;

import com.example.jsydq.R;
import com.example.jsydq.entity.Novel;
import com.example.jsydq.utils.UIUtils;

import java.util.List;

/**
 * Created by Lionheart on 2016/5/23.
 */
public class CZhanElement extends BaseElement<List<Novel>>{
    private ImageView[] ivs;
    @Override
    public void initView() {
        view = View.inflate(UIUtils.getContext(), R.layout.e_zhan,null);
        ivs=new ImageView[5];
        ivs[0]=(ImageView) view.findViewById(R.id.iv_1);
        ivs[1]=(ImageView) view.findViewById(R.id.iv_2);
        ivs[2]=(ImageView) view.findViewById(R.id.iv_3);
        ivs[3]=(ImageView) view.findViewById(R.id.iv_4);
        ivs[4]=(ImageView) view.findViewById(R.id.iv_5);
    }
    @Override
    public void fillView() {
        for(int i=0;i<5;i++){
            if(i<data.size()){
                ivs[i].setVisibility(View.VISIBLE);
                bu.display(ivs[i], data.get(i).getImgPath());
            }else{
                ivs[i].setVisibility(View.GONE);
            }

        }
    }

}

