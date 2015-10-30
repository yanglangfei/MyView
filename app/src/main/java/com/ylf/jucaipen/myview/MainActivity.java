package com.ylf.jucaipen.myview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends Activity {

    private TextView tv_plain;
    private EditText input;

    private AnimationDrawable animal;

    private FlowerView flowerView;
    // 屏幕宽度
    public static int screenWidth;
    // 屏幕高度
    public static int screenHeight;
    Timer myTimer = null;
    TimerTask mTask = null;
    private  ImageView iv_girl;
    private static final int SNOW_BLOCK = 1;
    private MediaPlayer player;




    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==SNOW_BLOCK){
                flowerView.inva();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        tv_plain= (TextView) findViewById(R.id.tv_plain);
        input= (EditText) findViewById(R.id.input);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //文字改变之前调用

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //文字改变时调用

            }

            @Override
            public void afterTextChanged(Editable s) {
                //文字改变之后调用
                if (checkEmail(s.toString())) {
                    tv_plain.setText("验证通过");
                    tv_plain.setTextColor(Color.GREEN);
                } else {
                    tv_plain.setText("验证失败");
                    tv_plain.setTextColor(Color.RED);
                }
            }
        });
        if(player==null){
            player=new MediaPlayer();
        }
        try {
            AssetFileDescriptor f= getResources().getAssets().openFd("song.mp3");
            player.setDataSource(f.getFileDescriptor(),f.getStartOffset(),f.getLength());
            player.prepare();
            player.start();
        } catch (IOException e) {


            e.printStackTrace();
        }
        flowerView= (FlowerView) findViewById(R.id.flowView);
        screenWidth = getWindow().getWindowManager().getDefaultDisplay()
                .getWidth();//获取屏幕宽度
        screenHeight = getWindow().getWindowManager().getDefaultDisplay()
                .getHeight();//获取屏幕高度

        iv_girl= (ImageView) findViewById(R.id.iv_girl);
        animal= (AnimationDrawable) iv_girl.getDrawable();
        ObjectAnimator animator=ObjectAnimator.ofFloat(iv_girl,"translationX",0f,-screenWidth);
        animator .setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(1000 * 30);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animations) {
                float values= (float) animations.getAnimatedValue();
            }
        });



        DisplayMetrics dis = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dis);
        float de = dis.density;
        flowerView.setWH(screenWidth, screenHeight, de);
        flowerView.loadFlower();
        flowerView.addRect();
        myTimer=new Timer();
        mTask=new TimerTask() {
            @Override
            public void run() {
             mHandler.obtainMessage(SNOW_BLOCK).sendToTarget();
            }
        };
        myTimer.schedule(mTask,3000,10);
        animator.start();
        animal.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flowerView.recly();
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


}
