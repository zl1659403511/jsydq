package com.example.jsydq.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Lionheart on 2016/5/23.
 */
public class SGridView extends GridView{
    public SGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public SGridView(Context context) {
        super(context);
    }
    public SGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(/*Integer.MAX_VALUE >>2*/500,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
