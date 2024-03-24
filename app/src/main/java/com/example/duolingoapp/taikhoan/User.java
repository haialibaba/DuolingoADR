package com.example.duolingoapp.taikhoan;

public class User {
    private String iduser;
    private String hoTen;
    private int point;
    private String email;
    private String password;
    private String SDT;

    public User() {
        //Nhận data từ Firebase
    }

    public User(String iduser, String hoTen, int point, String email, String password, String SDT) {
        this.iduser = iduser;
        this.hoTen = hoTen;
        this.point = point;
        this.email = email;
        this.password = password;
        this.SDT = SDT;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
}
