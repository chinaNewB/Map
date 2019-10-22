package com.example.qimo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


public class HomePage extends AppCompatActivity {

    private String loginUser;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FrameLayout frameLayout;
    private Button fujin,luxian,wode;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        fujin = findViewById(R.id.fujin);
        luxian = findViewById(R.id.luxian);
        wode = findViewById(R.id.wode);

        fujin.setTextColor(Color.GREEN);
        luxian.setTextColor(Color.LTGRAY);
        wode.setTextColor(Color.LTGRAY);
        fragmentManager = getSupportFragmentManager();
//        通过begin开启事务
        fragmentTransaction = fragmentManager.beginTransaction();
//        使用replace向容器内添加碎片
        fragmentTransaction.replace(R.id.listFragment,new ListFragment());
//        将事务添加到返回栈中
        fragmentTransaction.addToBackStack(null);

//        提交事务
        fragmentTransaction.commit();

        Intent intent = getIntent();
        loginUser = intent.getStringExtra("loginusername");
    }

    public String getUserName(){
        return loginUser;
    }


    public void Near(View v){
        fujin.setTextColor(Color.GREEN);
        luxian.setTextColor(Color.LTGRAY);
        wode.setTextColor(Color.LTGRAY);

        fragmentManager = getSupportFragmentManager();
//        通过begin开启事务
        fragmentTransaction = fragmentManager.beginTransaction();
//        使用replace向容器内添加碎片
        fragmentTransaction.replace(R.id.listFragment,new ListFragment());
//        将事务添加到返回栈中
        fragmentTransaction.addToBackStack(null);
//        拿到FrameLayout以便在设置其大小
//        frameLayout = (FrameLayout)findViewById(R.id.listFragment);
//        提交事务
        fragmentTransaction.commit();


    }

    public void Route(View v){
        fujin.setTextColor(Color.LTGRAY);
        luxian.setTextColor(Color.GREEN);
        wode.setTextColor(Color.LTGRAY);

        fragmentManager = getSupportFragmentManager();
//        通过begin开启事务
        fragmentTransaction = fragmentManager.beginTransaction();
//        使用replace向容器内添加碎片
        fragmentTransaction.replace(R.id.listFragment,new MapFragment());
//        将事务添加到返回栈中
        fragmentTransaction.addToBackStack(null);
//        拿到FrameLayout以便在设置其大小
        frameLayout = (FrameLayout)findViewById(R.id.listFragment);
//        提交事务
        fragmentTransaction.commit();
//        //强制更新
//        fragmentManager.executePendingTransactions();

    }

    public void Mine(View v) {
        fujin.setTextColor(Color.LTGRAY);
        luxian.setTextColor(Color.LTGRAY);
        wode.setTextColor(Color.GREEN);

        fragmentManager = getSupportFragmentManager();
//        通过begin开启事务
        fragmentTransaction = fragmentManager.beginTransaction();
//        使用replace向容器内添加碎片
        fragmentTransaction.replace(R.id.listFragment,new MyFragment());
//        将事务添加到返回栈中
        fragmentTransaction.addToBackStack(null);
//        拿到FrameLayout以便在设置其大小
        frameLayout = (FrameLayout)findViewById(R.id.listFragment);
//        提交事务
        fragmentTransaction.commit();
    }
}