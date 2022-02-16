package com.example.itijavaproject.pojo.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

@Entity(tableName = "user")
public class User {

    /***
     *   private int uid;
     *     @ColumnInfo(name="fName")
     *     private String fName;
     *     @ColumnInfo(name = "lname")
     *     private String lName;
     *     @ColumnInfo(name = "birthday")
     *     private Long birthday;
     *     //how to add column an a list
     *     private List<User> family;
     *     @ColumnInfo(name = "isMale")
     *     private boolean isMale;
     *     //how to add object as a column
     *     private Medicine medicine;
     */

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uid")
    private int uid;
    @ColumnInfo(name = "fullName")
    private String fullName;
    @ColumnInfo(name = "birthday")
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
