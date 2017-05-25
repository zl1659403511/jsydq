package com.example.jsydq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //首先调节透明度，当透明度变到0，跳转
        rl = (RelativeLayout) this.findViewById(R.id.rl);
        startAnim();
    }

    private void startAnim(){
        AlphaAnimation anim = new AlphaAnimation(0,1);
        anim.setDuration(500);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //当动画结束的时候，跳转
                tiaozhuan();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rl.startAnimation(anim);
    }

    private void tiaozhuan(){
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        finish();
    }
}
