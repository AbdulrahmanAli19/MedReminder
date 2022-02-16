package com.example.itijavaproject.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uid")
    private int uid;
    @ColumnInfo(name="fName")
    private String fName;
    @ColumnInfo(name = "lname")
    private String lName;
    @ColumnInfo(name = "birthday")
    private Long birthday;
    //how to add column an a list
    private List<User> family;
    @ColumnInfo(name = "isMale")
    private boolean isMale;
    //how to add object as a column
    private Medicine medicine;

    public User() {
    }

    public User(int uid, String fName, String lName, Long birthday, List<User> family, boolean isMale,Medicine medicine) {
        this.uid = uid;
        this.fName = fName;
        this.lName = lName;
        this.birthday = birthday;
        this.family = family;
        this.isMale = isMale;
        this.medicine=medicine;
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
