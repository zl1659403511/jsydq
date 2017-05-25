package com.example.jsydq.bz;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.example.jsydq.R;
import com.example.jsydq.service.ContentService;
import com.example.jsydq.utils.UIUtils;

import java.util.List;

/**
 * Created by Lionheart on 2016/5/18.
 */
public class BZContainer implements View.OnTouchListener{
    private Bitmap currentBitmap, nextBitmap;
    private Canvas currentCanvas, nextCanvas;
    private Bitmap bg = null;
    private int width = 700;
    private int height = 1280;
    private int fontSize = 32;
    private int textColor = Color.BLACK;
    private int marginWidth = 15; // 左右与边缘的距离
    private int marginHeight = 15; // 上下与边缘的距离

    private int lineCount; // 每页可以显示的行数
    private float visibleHeight; // 绘制内容的高
    private float visibleWidth; // 绘制内容的宽

    private Paint normalPaint;
    private Paint titlePaint;
    private Activity activity;
    private BzView bzView;
    private ContentService contentService;

    public BZContainer(Activity activity) {
        // TODO Auto-generated constructor stub
        this.activity = activity;
        init(activity);
       // pageView = new SimplePageView(activity, width, height);
        bzView = new BzView(activity, width, height);
        activity.setContentView(bzView);
        currentBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        nextBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        currentCanvas = new Canvas(currentBitmap);
        nextCanvas = new Canvas(nextBitmap);
        normalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        normalPaint.setTextAlign(Paint.Align.LEFT);
        normalPaint.setTextSize(fontSize);
        normalPaint.setColor(textColor);

        titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTextSize(40);
        titlePaint.setColor(Color.MAGENTA);
        visibleWidth = width - marginWidth * 2;
        visibleHeight = height - marginHeight * 2;
        lineCount = (int) (visibleHeight / fontSize); // 可显示的行数
    }

    private void init(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;
        height = metric.heightPixels;
        bg = BitmapFactory.decodeResource(activity.getResources(),
                R.drawable.bg_read);
    }
    public void initDraw() {
        contentService = new ContentService();
        draw(currentCanvas);
        bzView.setBitmaps(currentBitmap, currentBitmap);
        bzView.setOnTouchListener(this);
    }


    //画背景和文字
    public void draw(Canvas c) {
        List<String> lines = contentService.getLines();
        if(null!=lines&&lines.size()>0){
            c.drawBitmap(bg,0,0,null);
            int y = contentService.normalMargin;
            //绘制标题
            if(contentService.currentPage==0){
                y+=contentService.normalMargin;
                y+=contentService.titleSize;
                c.drawText(contentService.getTitle(),contentService.getTitleMargin(),y,contentService.titlePaint);
                y+=contentService.normalMargin;
            }
            //绘制正文
            for(int i =0;i<lines.size();i++){
                String line  = lines.get(i);
                y+=contentService.normalSize;
                if(line.startsWith(" ")){
                    c.drawText(line,10+2*contentService.normalSize,y,contentService.normalPaint);
                }else{
                    c.drawText(line,10,y,contentService.normalPaint);
                }
                y+=contentService.normalMargin;
            }
        }

    }
    @Override
    public boolean onTouch(View v, MotionEvent e) {
        boolean ret;
        if (v == bzView) {
            //点击了一下当前页面
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                //停止动画
                bzView.abortAnimation();
                bzView.calcCornerXY(e.getX(), e.getY());
                draw(currentCanvas);
                //向右
                if(bzView.DragToRight()){
                    if(contentService.isFirstPage()){
                        Toast.makeText(UIUtils.getContext(), "已经是第一页了", Toast.LENGTH_SHORT).show();
                        return false;
                    }else{
                        contentService.prePage();
                    }
                    draw(nextCanvas);
                }else{
                    if(contentService.isLastPage()){
                        Toast.makeText(UIUtils.getContext(), "已经是末页了", Toast.LENGTH_SHORT).show();
                        return false;
                    }else{
                        contentService.nextPage();
                    }
                    draw(nextCanvas);
                }
                bzView.setBitmaps(currentBitmap, nextBitmap);
            }
            //自己调用onDraw
            ret = bzView.doTouchEvent(e);
            return ret;
        }
        return false;
    }
}
