package com.example.qimo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dao.DBHelper;
import com.example.info.User_info;

public class MainActivity extends AppCompatActivity {

    private Button regist_new,login;
    private Button regist;
    private EditText username,pwd,pwdagain,phone,email;
    private EditText user,password;
    private DBHelper user_dao;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_dao = new DBHelper(this);

        user = findViewById(R.id.user);
        password = findViewById(R.id.password);

        regist_new = findViewById(R.id.regist_new);
        login = findViewById(R.id.login);


        //注册新用户按钮的监听事件
        regist_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                final View view = layoutInflater.inflate(R.layout.regist,null);

                //注册页面的一些填写的资料
                username = view.findViewById(R.id.username);    //用户名
                pwd = view.findViewById(R.id.password);         //密码
                pwdagain = view.findViewById(R.id.passwordAgain);//确认密码
                phone = view.findViewById(R.id.phone);          //电话
                email = view.findViewById(R.id.email);          //email邮箱
                regist = view.findViewById(R.id.regist);        //注册按钮

                builder.setTitle("填写信息并注册")
                        .setIcon(R.drawable.ic_launcher)
                        .setMessage("* 密码由8-16位数字、字符或符号组成")
                        .setView(view)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                         }).show();

                //注册按钮，页面交互
                regist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String uname = username.getText().toString();
                        final String upwd = pwd.getText().toString();
                        final String upwdagain = pwdagain.getText().toString();
                        final String uphone = phone.getText().toString();
                        final String uemail = email.getText().toString();

                        if (uname.isEmpty()){
                            username.setError("用户名不能为空");
                            Toast.makeText(MainActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                        }else if(upwd.isEmpty()){
                            pwd.setError("密码不能为空");
                            Toast.makeText(MainActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                        }else if(upwdagain.isEmpty()){
                            pwdagain.setError("请确认密码");
                            Toast.makeText(MainActivity.this,"请确认密码",Toast.LENGTH_SHORT).show();
                        }else{
                            //不空之后的判断
                            User_info user_info = new User_info();
                            if(user_dao.findOne(uname)){
                                username.setError("用户名已存在");
                                Toast.makeText(MainActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                            }else{
                                if(!upwdagain.equals(upwd)){
                                    pwdagain.setError("确认密码错误");
                                }else{
                                    user_info.setUsername(uname);
                                    user_info.setPassword(upwd);
                                    user_info.setPhone(uphone);
                                    user_info.setEmail(uemail);
                                    user_dao.insert(user_info);
                                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(MainActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }
                });

                }
        });


        //登录按钮监听事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String l_username = user.getText().toString();
                final String l_password = password.getText().toString();

                if(l_username.isEmpty()){
                    user.setError("用户名不能为空");
                    Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                }else if(l_password.isEmpty()){
                    password.setError("密码不能为空");
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    //判断完成，点击按钮页面交互
                    boolean user_info = user_dao.findOnetologin(l_username, l_password);
                    if(user_info){
                        Intent intent = new Intent(getApplicationContext(),HomePage.class);
                        intent.putExtra("loginusername",l_username);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "登录成功",Toast.LENGTH_SHORT).show();
                    }else{
                        if(!user_dao.findOne(l_username)){
                            user.setError("该账号不存在");
                            Toast.makeText(MainActivity.this, "该账号不存在",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            new AlertDialog.Builder(MainActivity.this)
                                    .setIcon(R.drawable.ic_launcher)
                                    .setTitle("错误").setMessage("密码错误")
                                    .setPositiveButton("确定", null).show();
                        }
                    }
                }
            }
        });

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
