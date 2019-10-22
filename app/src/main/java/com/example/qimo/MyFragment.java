package com.example.qimo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dao.DBHelper;
import com.example.info.User_info;

public class MyFragment extends Fragment  {

    private String loginUser;
    private EditText newPwd,newPwdAgain;
    private Button OkForChange;
    private  View view;
    private String newPwds,newPwdAgains;
    private DBHelper user_dao;
    private TextView lusername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        此处数据类型为View，所以直接返回inflater即可
        view = inflater.inflate(R.layout.mine_fragment, container, false);
        if(view != null){
            initView();
        }
        HomePage activity = (HomePage) getActivity();
        loginUser = activity.getUserName();
        lusername.setText(loginUser);
        return view;
    }

    private void initView(){
        newPwd = view.findViewById(R.id.newPwd);
        newPwdAgain = view.findViewById(R.id.newPwdAgain);
        OkForChange = view.findViewById(R.id.OkForChange);
        lusername = view.findViewById(R.id.lusername);
        user_dao = new DBHelper(getActivity());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        OkForChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPwds = newPwd.getText().toString();
                newPwdAgains = newPwdAgain.getText().toString();

                if (newPwds.length()<=0){
                    Toast.makeText(getActivity(),"新密码不能为空",Toast.LENGTH_SHORT).show();
                }else if(newPwdAgains.length()<=0){
                    Toast.makeText(getActivity(),"请确认密码",Toast.LENGTH_SHORT).show();
                }else{
                    if(!newPwdAgains.equals(newPwds)){
                        newPwdAgain.setError("确认密码错误");
                        Toast.makeText(getActivity(),"确认密码错误",Toast.LENGTH_SHORT).show();
                    }else{
                        User_info user_info = new User_info();
                        user_info.setUsername(loginUser);
                        user_info.setPassword(newPwds);
                        user_dao.update(user_info);
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getActivity(),"修改成功，请重新登录",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}
