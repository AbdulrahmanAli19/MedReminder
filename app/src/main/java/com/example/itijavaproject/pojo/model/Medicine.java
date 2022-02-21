package com.example.itijavaproject.pojo.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.itijavaproject.data.db.TimeTypeConverters;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "Medicine")
@TypeConverters(TimeTypeConverters.class)
public class Medicine implements Serializable {
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
    @ColumnInfo (name = "noOfStrength")
    private int noOfStrength;
    @ColumnInfo(name = "isActive")
    private boolean isActive=true;
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
    @ColumnInfo(name="endDate")
    private Long endDate;


    public Medicine() {
    }

    public Medicine(String name, String iconType, String strength, boolean isActive, String instructions,
                    String reason, boolean isRefillReminder, int numOfPills, List<Long> times,
                    int frequencyPerDay, String duration,Long startDate,int noOfStrength,Long endDate) {
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
        this.endDate=endDate;
        this.noOfStrength=noOfStrength;

    }

    public int getMed_id() {
        return med_id;
    }

    public void setMed_id(int med_id) {
        this.med_id = med_id;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public int getNoOfStrength() {
        return noOfStrength;
    }

    public void setNoOfStrength(int noOfStrength) {
        this.noOfStrength = noOfStrength;
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



    @Override
    public String toString() {
        return "Medicine{" +
                "medID=" + med_id +
                ", name='" + name + '\'' +
                ", iconType='" + iconType + '\'' +
                ", strength='" + strength + '\'' +
                ", noOfStrength=" + noOfStrength +
                ", isActive=" + isActive +
                ", instructions='" + instructions + '\'' +
                ", reason='" + reason + '\'' +
                ", isRefillReminder=" + isRefillReminder +
                ", numOfPills=" + numOfPills +
                ", times=" + times +
                ", frequencyPerDay=" + frequencyPerDay +
                ", duration='" + duration + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
