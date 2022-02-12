package com.example.itijavaproject.model;

import java.util.List;


public class User {

    private int uid;
    private String fName;
    private String lName;
    private Long birthday;
    private List<User> family;
    private boolean isMale;

    public User() {
    }

    public User(int uid, String fName, String lName, Long birthday, List<User> family, boolean isMale) {
        this.uid = uid;
        this.fName = fName;
        this.lName = lName;
        this.birthday = birthday;
        this.family = family;
        this.isMale = isMale;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public List<User> getFamily() {
        return family;
    }

    public void setFamily(List<User> family) {
        this.family = family;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }
}
