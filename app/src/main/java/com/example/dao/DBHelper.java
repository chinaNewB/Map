package com.example.dao;

import java.util.ArrayList;

import com.example.info.User_info;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper
{
    private static final String DATABASE_NAME="mydb";
    private static final int DATABASE_VERSION=1;//数据库版本
    private static final String TABLE_NAME="users";
    private static final String[] COLUMNS=
    { "id", "username", "password" ,"phone","email"};
    private String sql="";

    private DBOpenHelper helper;//调用
    private SQLiteDatabase db;//数据库的访问类P116

    public DBHelper(Context context)
    {
        sql="create table " + TABLE_NAME + " (" + COLUMNS[0] + " long primary key, " //创建表
        										+ COLUMNS[1] + " varchar(50)," 
        										+ COLUMNS[2] + " varchar(50),"
        										+ COLUMNS[3] + " varchar(20),"
        										+ COLUMNS[4] + " varchar(50));";
        helper=new DBOpenHelper(context, DATABASE_NAME, DATABASE_VERSION, TABLE_NAME, sql);
        db=helper.getWritableDatabase();
    }

    public void insert(User_info data)
    {
    	SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values=new ContentValues();//存储
        values.put(COLUMNS[0], data.getId());
        values.put(COLUMNS[1], data.getUsername());
        values.put(COLUMNS[2], data.getPassword());
        values.put(COLUMNS[3], data.getPhone());
        values.put(COLUMNS[4], data.getEmail());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<User_info> find()
    {
    	SQLiteDatabase db=helper.getReadableDatabase();
        ArrayList<User_info> list=new ArrayList<User_info>();
        User_info user_info=null;
        Cursor cursor=db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);//指针游标119
        while(cursor.moveToNext())
        {
        	user_info=new User_info();
        	user_info.setId(cursor.getLong(0));
        	user_info.setUsername(cursor.getString(1));
        	user_info.setPassword(cursor.getString(2));
        	user_info.setPhone(cursor.getString(3));
        	user_info.setEmail(cursor.getString(4));
            list.add(user_info);
        }
        cursor.close();
        return list;
    }
    
    public int update(User_info user_info){
    	SQLiteDatabase db = helper.getWritableDatabase();
    	ContentValues values = new ContentValues();	//要修改的数据
    	values.put("username",user_info.getUsername());
    	values.put("password",user_info.getPassword());
    	values.put("phone",user_info.getPhone());
    	values.put("email",user_info.getEmail());
    	int count=db.update(TABLE_NAME, values, "username=?", new String[]{user_info.getUsername()+""});//更新并得到行数
    	db.close();
    	return count;
    }
    
    public boolean findOne(String username){
    	SQLiteDatabase db = helper.getReadableDatabase();
    	Cursor c = db.query(TABLE_NAME,null,"username=?",new String[]{username},null,null,null);
    	boolean result = c.moveToNext();
    	c.close();
    	db.close();
    	return result;
    }
    
    public boolean findOnetologin(String username,String password){
    	SQLiteDatabase db = helper.getReadableDatabase();
    	Cursor c = db.query(TABLE_NAME,null,"username=? and password=?",new String[]{username,password},null,null,null);
    	boolean result = c.moveToNext();
    	c.close();
    	db.close();
    	return result;
    }
 
    
    public boolean findPhone(String username){
    	SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select phone from "+TABLE_NAME+" where username=?";
		Cursor c = db.rawQuery(sql, new String[]{username});
		boolean result = c.moveToNext();
		c.close();
		db.close();
		return result;
    }
    
    public boolean findEmail(String username){
    	SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select email from "+TABLE_NAME+" where username=?";
		Cursor c = db.rawQuery(sql,  new String[]{username});
		boolean result = c.moveToNext();
		c.close();
		db.close();
		return result;
    }
    
    public boolean findPassword(String username){
    	SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select password from "+TABLE_NAME+" where username=?";
		Cursor c = db.rawQuery(sql,  new String[]{username});
		boolean result = c.moveToNext();
		c.close();
		db.close();
		return result;
    }
}
