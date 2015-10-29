package com.ylf.jucaipen.myview;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView tv_plain;
    private EditText input;

    private FlowerView flowerView;
    // 屏幕宽度
    public static int screenWidth;
    // 屏幕高度
    public static int screenHeight;
    Timer myTimer = null;
    TimerTask mTask = null;
    private  ImageView iv_girl;
    private static final int SNOW_BLOCK = 1;
    private RelativeLayout rl;
    private  int bgs[]={R.drawable.snow_sence,R.drawable.snow_sence1,R.drawable.snow_sence2,R.drawable.snow_sence3,R.drawable.snow_sence4};

    private ViewPager vp_snowSence;
    private  int position;

    private List<View> views=new ArrayList<View>();
    private  SnowAdapter adapter;



    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==SNOW_BLOCK){
                flowerView.inva();
            }else {
                //t  3   p
                if(position<=3) {
                    rl.setBackgroundResource(bgs[position++]);
                }else {
                    position=0;
                }
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

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                   if(checkEmail(s.toString())){
                       tv_plain.setText("验证通过");
                       tv_plain.setTextColor(Color.GREEN);
                   }else {
                       tv_plain.setText("验证失败");
                       tv_plain.setTextColor(Color.RED);
                   }
            }
        });

        rl= (RelativeLayout) findViewById(R.id.bg);
        vp_snowSence= (ViewPager) findViewById(R.id.vp_snow_sence);
        View view1=new View(this);
        view1.setBackground(getResources().getDrawable(R.drawable.snow_sence));
        View view2=new View(this);
        view2.setBackground(getResources().getDrawable(R.drawable.snow_sence1));
        View view3=new View(this);
        view3.setBackground(getResources().getDrawable(R.drawable.snow_sence2));
        View view4=new View(this);
        view4.setBackground(getResources().getDrawable(R.drawable.snow_sence3));
        View view5=new View(this);
        view5.setBackground(getResources().getDrawable(R.drawable.snow_sence4));
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
       adapter=new SnowAdapter(views);
        vp_snowSence.setAdapter(adapter);
        iv_girl= (ImageView) findViewById(R.id.iv_girl);
        ObjectAnimator animator=ObjectAnimator.ofFloat(iv_girl,"translationX",0f,-2000f);
        animator .setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setDuration(1000 * 30);
        animator.start();


        flowerView= (FlowerView) findViewById(R.id.flowView);
        screenWidth = getWindow().getWindowManager().getDefaultDisplay()
                .getWidth();//获取屏幕宽度
        screenHeight = getWindow().getWindowManager().getDefaultDisplay()
                .getHeight();//获取屏幕高度

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
