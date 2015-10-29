package com.ylf.jucaipen.myview;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/10/29.
 */
public class TestActivity extends Activity {
    private ImageView iv_girl;
    private Animation animation;
    private  float currentStep=0f;
    private  float step=10f;
    private Handler mHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(iv_girl,"translationX",currentStep++,step);
            objectAnimator.setDuration(100);
            objectAnimator.start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_test);
        initView();
    }

    private void initView() {
        iv_girl= (ImageView) findViewById(R.id.iv_girl);
        animation= AnimationUtils.loadAnimation(this,R.anim.sence);
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(iv_girl,"translationX",0f,10f);
        objectAnimator.setDuration(100);
        iv_girl.startAnimation(animation);
        objectAnimator.start();
        new Thread(){
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(100);
                        mHandle.obtainMessage(100).sendToTarget();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
