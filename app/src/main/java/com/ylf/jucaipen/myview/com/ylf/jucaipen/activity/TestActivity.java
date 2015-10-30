package com.ylf.jucaipen.myview.com.ylf.jucaipen.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ylf.jucaipen.myview.R;

/**
 * Created by Administrator on 2015/10/30.
 */
public class TestActivity  extends Activity {

    private EditText et_name;
    private  EditText et_pwd;

    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_test);
        initView();
    }

    private void initView() {
        btn_submit= (Button) findViewById(R.id.btn_submit);
        et_name= (EditText) findViewById(R.id.et_name);
        et_pwd= (EditText) findViewById(R.id.et_pwd);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                String name=et_name.getText().toString();
                String pwd=et_pwd.getText().toString();
                if(name.length()>0&&pwd.length()>0) {
                    et_pwd.setEnabled(false);
                    et_pwd.setBackground(null);
                    et_name.setBackground(null);
                    et_name.setEnabled(false);
                }
            }
        });
    }
}
