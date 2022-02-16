package com.example.itijavaproject.pojo.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.itijavaproject.data.db.TimeTypeConverters;

import java.util.List;

@Entity(tableName = "Medicine")
@TypeConverters(TimeTypeConverters.class)
public class Medicine {
    @ColumnInfo(name = "medID")
    @PrimaryKey
    @NonNull
    private int med_id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "iconType")
    private int iconType;
    @ColumnInfo(name = "strength")
    private String strength;
    @ColumnInfo(name = "isActive")
    private boolean isActive;
    @ColumnInfo(name = "instructions")
    private String instructions;
    @ColumnInfo(name = "reason")
    private String reason;
    @ColumnInfo(name = "refillReminder")
    private int refillReminder;
    @ColumnInfo(name = "numOfPills")
    private int numOfPills;
    private List<Long> times;
    @ColumnInfo(name = "duration")
    private String duration;
    @ColumnInfo(name = "frequencyPerDay")
    private int frequencyPerDay;
    @ColumnInfo(name = "periodTime")
    private String periodTime;


    public Medicine() {
    }

    public Medicine(int med_id, String name, int icon, String strength, String medType,
                    boolean isActive, String instructions, String reason, int refillReminder,
                    int numOfPills, List<Long> times, String duration, int frequencyPerDay, String periodTime) {
        this.med_id = med_id;
        this.name = name;
        this.iconType = icon;
        this.strength = strength;
        this.isActive = isActive;
        this.instructions = instructions;
        this.reason = reason;
        this.refillReminder = refillReminder;
        this.numOfPills = numOfPills;
        this.times = times;
        this.frequencyPerDay = frequencyPerDay;
        this.duration = duration;
        this.periodTime = periodTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getFrequencyPerDay() {
        return frequencyPerDay;
    }

    public void setFrequencyPerDay(int frequencyPerDay) {
        this.frequencyPerDay = frequencyPerDay;
    }

    public String getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(String periodTime) {
        this.periodTime = periodTime;
    }

    public int getMedID() {
        return med_id;
    }

    public void setMedID(int medID) {
        this.med_id = medID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconType() {
        return iconType;
    }

    public void setIconType(int iconType) {
        this.iconType = iconType;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getRefillReminder() {
        return refillReminder;
    }

    public void setRefillReminder(int refillReminder) {
        this.refillReminder = refillReminder;
    }

    public int getNumOfPills() {
        return numOfPills;
    }

    public void setNumOfPills(int numOfPills) {
        this.numOfPills = numOfPills;
    }

    public List<Long> getTimes() {
        return times;
    }

    public void setTimes(List<Long> times) {
        this.times = times;
    }

    public int getMed_id() {
        return med_id;
    }

    public void setMed_id(int med_id) {
        this.med_id = med_id;
    }
}
