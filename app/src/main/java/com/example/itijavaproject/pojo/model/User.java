package com.example.itijavaproject.pojo.model;

import java.util.List;


public class User {

    private int uid;
    private String fullName;
    private Long birthday;
    private List<User> family;
    private String email;
    private String phoneNumber;
    private boolean isMale;
    private Medicine medicine;

    public User() {
    }

    public User(int uid, String fullName, String email, String phoneNumber, Long birthday,
                List<User> family, boolean isMale, Medicine medicine) {
        this.uid = uid;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.family = family;
        this.isMale = isMale;
        this.medicine = medicine;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
