package com.example.info;

public class User_info{
    private Long id;
    private String username;
    private String password;
    private String phone;
    private String Email;
    //set设置get获取
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }


    public User_info() {
        super();
    }
    public User_info(String username, String password,
                     String phone, String email) {
        super();
        this.username = username;
        this.password = password;
        this.phone = phone;
        Email = email;
    }
    public User_info(Long id, String username, String password,
                     String phone, String email) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        Email = email;
    }
    @Override
    public String toString() {
        return "User_info [id=" + id + ", 用户名=" + username + ""
                + ", 地址=" + phone + ", 邮箱=" + Email + "]";
    }

}