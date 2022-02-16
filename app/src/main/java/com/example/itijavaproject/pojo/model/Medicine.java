package com.example.itijavaproject.pojo.model;

import java.util.List;

public class Medicine {
    private int medID;
    private String name;
    private int iconType;
    private String strength;
    private boolean isActive;
    private String instructions;
    private String reason;
    private int refillReminder;
    private int numOfPills;
    private List<Long> times;
    private String duration;
    private int frequencyPerDay;
    private String periodTime;



    public Medicine() {
    }

    public Medicine(int medID, String name, int icon, String strength, String medType,
                    boolean isActive, String instructions, String reason, int refillReminder,
                    int numOfPills, List<Long> times, String duration,int frequencyPerDay,String periodTime) {
        this.medID = medID;
        this.name = name;
        this.iconType = icon;
        this.strength = strength;
        this.isActive = isActive;
        this.instructions = instructions;
        this.reason = reason;
        this.refillReminder = refillReminder;
        this.numOfPills = numOfPills;
        this.times = times;
        this.frequencyPerDay=frequencyPerDay;
        this.duration=duration;
        this.periodTime=periodTime;
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


}
