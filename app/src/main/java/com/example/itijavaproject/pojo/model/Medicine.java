package com.example.itijavaproject.pojo.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.itijavaproject.data.db.TimeTypeConverters;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "Medicine")
@TypeConverters(TimeTypeConverters.class)
public class Medicine implements Serializable {

    @ColumnInfo(name = "med_id")
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int medID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "iconType")
    private String iconType;

    @ColumnInfo(name = "strength")
    private String strength;

    @ColumnInfo(name = "noOfStrength")
    private int noOfStrength;

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

    @ColumnInfo(name = "endDate")
    private Long endDate;


    public Medicine() {
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

    public int getMedID() {
        return medID;
    }

    public void setMedID(int medID) {
        this.medID = medID;
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

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "medID=" + medID +
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
