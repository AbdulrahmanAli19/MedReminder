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
    @ColumnInfo(name = "med_id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int med_id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "iconType")
    private String iconType;
    @ColumnInfo(name = "strength")
    private String strength;
    @ColumnInfo(name = "isActive")
    private boolean isActive;
    @ColumnInfo(name = "instructions")
    private String instructions;
    @ColumnInfo(name = "reason")
    private String reason;
    @ColumnInfo(name = "refillReminder")
    private boolean isRefillReminder;
    @ColumnInfo(name = "numOfPills")
    private int numOfPills;
    private List<Long> times;
    @ColumnInfo(name = "frequencyPerDay")
    private int frequencyPerDay;
    @ColumnInfo(name = "duration")
    private String duration;
    @ColumnInfo(name = "startDate")
    private Long startDate;

    public Medicine() {
    }

    public Medicine(String name, String iconType, String strength, boolean isActive, String instructions,
                    String reason, boolean isRefillReminder, int numOfPills, List<Long> times,
                    int frequencyPerDay, String duration,Long startDate) {
        this.name = name;
        this.iconType = iconType;
        this.strength = strength;
        this.isActive = isActive;
        this.instructions = instructions;
        this.reason = reason;
        this.isRefillReminder = isRefillReminder;
        this.numOfPills = numOfPills;
        this.times = times;
        this.frequencyPerDay = frequencyPerDay;
        this.duration = duration;
        this.startDate=startDate;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public int getFrequencyPerDay() {
        return frequencyPerDay;
    }

    public void setFrequencyPerDay(int frequencyPerDay) {
        this.frequencyPerDay = frequencyPerDay;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String periodTime) {
        this.duration = duration;
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

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
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

    public boolean getIsRefillReminder() {
        return isRefillReminder;
    }

    public void setIsRefillReminder(boolean isRefillReminder) {
        this.isRefillReminder = isRefillReminder;
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
