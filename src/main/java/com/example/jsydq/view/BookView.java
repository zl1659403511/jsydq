package com.example.jsydq.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jsydq.R;
import com.example.jsydq.ReadActivity;
import com.example.jsydq.entity.Chapter;
import com.example.jsydq.entity.ChapterSum;
import com.example.jsydq.entity.Novel;
import com.example.jsydq.fragment.ShelfFragment;
import com.example.jsydq.request.BaseRequest;
import com.example.jsydq.request.BookRequest;
import com.example.jsydq.request.SummaryRequest;
import com.example.jsydq.utils.Constants;
import com.example.jsydq.utils.UIUtils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Lionheart on 2016/6/2.
 */
//本身是一个relativelayout
public class BookView extends RelativeLayout implements Animator.AnimatorListener {
    public static BookView instance;
    private static final int OPEN_TIME = 1000;
    public static final int  CLOSE_TIME= 1000;
    // Animation background scales
    private float bgScaleX;
    private float bgScaleY;
    // 那个变化的图片，宽度不是放大到整个屏幕
    private float coverScaleX;
    private float coverScaleY;
    private int[] selfLocation = new int[2];
    private WindowManager wm;
    private FrameLayout fl;
    private ImageView iv_cover = null;
    private ImageView iv_animCover;
    private ImageView iv_bg;


    private float openBookEndX = 0;
    private float openBookEndY = 50;
    private Context context;

    private boolean isOpen = false;
    private int animCount = 0;
    private int totalAnim = 0;



    public BookView(Context context) {
        this(context, null);
        this.context = context;
        wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        initListener();
    }

    public BookView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
        wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        initListener();
    }

    public BookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        initListener();
    }


    private void initListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOpen) {
                    GridView gv = (GridView) view.getParent();
                    int i = gv.indexOfChild(view);
                    Novel currentNovel = ShelfFragment.novels.get(i);
                    Integer novelId = currentNovel.getId();
                    //保存
                    Constants.novelId = novelId;
                    instance = BookView.this;
                    startOpenBookAnimation();
                }
            }
        });
    }

    public synchronized void startOpenBookAnimation() {
        startOpenBookAnimation(getParent());
    }

    public synchronized void startOpenBookAnimation(ViewParent parent) {
        if (!isOpen) {
            //未开启动画时
            iv_cover = (ImageView) findViewById(R.id.iv_book_cover);
            if (iv_cover == null/* || mBackground == null */) {
                //如果图书封面空
                return;
            }
            //初始化一个rootView
            fl = new FrameLayout(context);
            //找到这个view在window中的绝对位置,方法调用之后，selfLocation就有值
            getLocationInWindow(selfLocation);
            //置于顶部
            wm.addView(fl, getDefaultWindowParams());
            // 向左淡出的那个view
            iv_animCover = new ImageView(context);
            //设置一个imageview
            iv_animCover.setScaleType(iv_cover.getScaleType());
            iv_animCover.setImageDrawable(iv_cover.getDrawable());

            iv_bg = new ImageView(context);
            //iv_bg.setScaleType(iv_cover.getScaleType());
            iv_bg.setBackgroundResource(R.drawable.bg_cover);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            ViewGroup.LayoutParams params = iv_cover.getLayoutParams();
            //fl添加两个与iv_cover一样大小的imageview
            fl.addView(iv_bg, params);
            fl.addView(iv_animCover, params);

            //DisplayMetrics 类提供了一种关于显示的通用信息，如显示大小，分辨率和字体。
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int screenWidth = dm.widthPixels;
            int screenHeight = dm.heightPixels;
            //宽度放大倍数
            float scaleW = screenWidth / (float) iv_cover.getWidth();
            //高度放大倍数
            float scaleH = screenHeight / (float) iv_cover.getHeight();
            //选一个放大的倍数大的，这样能铺屏
            float baseScale = Math.max(scaleW, scaleH);
            bgScaleX = baseScale;
            bgScaleY = baseScale;
            coverScaleX = baseScale / 3;
            //但高度，要放大到整个倍数
            coverScaleY = baseScale;
            startFlipCoverAnimation();
        }
    }

    private void startFlipCoverAnimation() {
        iv_bg.setPivotX(0);
        iv_bg.setPivotY(0);
        iv_animCover.setPivotX(0);
        iv_animCover.setPivotY(0);
        totalAnim = 0;
        startIndividualAnim(iv_bg, "translationX", selfLocation[0], openBookEndX, true);
        startIndividualAnim(iv_bg, "translationY", selfLocation[1], openBookEndY, true);
        startIndividualAnim(iv_bg, "scaleX", 1.0f, bgScaleX, true);
        startIndividualAnim(iv_bg, "scaleY", 1.0f, bgScaleY, true);
        startIndividualAnim(iv_animCover, "translationX", selfLocation[0], openBookEndX, true);
        startIndividualAnim(iv_animCover, "translationY", selfLocation[1], openBookEndY, true);
        startIndividualAnim(iv_animCover, "scaleX", 1.0f, coverScaleX, true);
        startIndividualAnim(iv_animCover, "scaleY", 1.0f, coverScaleY, true);
        //左负右正
        startIndividualAnim(iv_animCover, "rotationY", 0, -100, true);
    }

    private WindowManager.LayoutParams getDefaultWindowParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, 0, 0, WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_FULLSCREEN, PixelFormat.RGBA_8888);
        return params;
    }

    public synchronized void startCloseBookAnimation() {
        totalAnim = 0;
        startIndividualAnim(iv_bg, "translationX", openBookEndX, selfLocation[0], false);
        startIndividualAnim(iv_bg, "translationY", openBookEndY, selfLocation[1], false);
        startIndividualAnim(iv_bg, "scaleX", bgScaleX, 1.0f, false);
        startIndividualAnim(iv_bg, "scaleY", bgScaleY, 1.0f, false);
        // cover animation
        startIndividualAnim(iv_animCover, "translationX", openBookEndX, selfLocation[0], false);
        startIndividualAnim(iv_animCover, "translationY", openBookEndY, selfLocation[1], false);
        startIndividualAnim(iv_animCover, "scaleX", coverScaleX, 1.0f, false);
        startIndividualAnim(iv_animCover, "scaleY", coverScaleY, 1.0f, false);
        startIndividualAnim(iv_animCover, "rotationY", -100, 0, false);
    }

    private void startIndividualAnim(View target, String property, float startValue, float endValue, boolean isOpen) {
        totalAnim++;
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, property, startValue, endValue).setDuration(
                isOpen ? OPEN_TIME : CLOSE_TIME);
        animator.addListener(this);
        animator.start();
    }


    public void removeWindowView() {
        isOpen = false;
        if (fl != null) {
            wm.removeView(fl);
            fl = null;
        }
    }

    @Override
    public void onAnimationEnd(Animator arg0) {
        if (!isOpen) {
            animCount++;
            if (animCount>= totalAnim) {
                isOpen = true;
                //不能直接跳转
                Constants.cIndex =1;
                Constants.chapterCount = 0;
                Constants.curChapter = null;
                SummaryRequest summaryRequest = new SummaryRequest();
                summaryRequest.setInter(new BaseRequest.BaseInter<ChapterSum>() {
                    @Override
                    public void handleData(ChapterSum data) {
                        if(null==data){
                            //从展开的动画回去
                            BookView.instance.startCloseBookAnimation();
                        }else{
                            if(data.getNum()<=0){
                                BookView.instance.startCloseBookAnimation();
                            }else{

                                Constants.chapterCount = data.getNum();
                                Constants.cIndex =1;
                                //n1_1.json
                                String url = data.getPaths().get(0);
                                BookRequest bookRequest = new BookRequest();
                                bookRequest.setInter(new BaseRequest.BaseInter<Chapter>() {
                                    @Override
                                    public void handleData(Chapter data) {
                                        if (null != data) {
                                            Constants.curChapter = data;
                                            Message msg = Message.obtain();
                                            msg.what = Constants.OK;
                                            handler.sendMessage(msg);
                                        }
                                    }
                                });
                                bookRequest.firstLoad(url);
                                BookRequest bookRequest2 = new BookRequest();
                                for(int i=1;i<Constants.chapterCount;i++){
                                    bookRequest2.firstLoad(data.getPaths().get(i));
                                }
                            }
                        }
                    }
                });
                summaryRequest.firstLoad("chapterSum.shtml?novelId="+Constants.novelId);
            }
        } else {
                animCount--;
                if (animCount<=0) {
                    removeWindowView();
                }
        }
    }
    @Override
    public void onAnimationRepeat(Animator arg0) {
    }
    @Override
    public void onAnimationStart(Animator arg0) {
    }

    @Override
    public void onAnimationCancel(Animator arg0) {
    }
    private void startActivity() {
        context.startActivity(new Intent(context, ReadActivity.class));
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case Constants.OK:
                    startActivity();
                    break;
                case Constants.ERROR:
                    break;
            }
        }
    };
}
