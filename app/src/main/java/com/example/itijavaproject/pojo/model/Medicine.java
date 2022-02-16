package com.example.itijavaproject.pojo.model;

import androidx.room.Entity;

import java.util.List;

@Entity(tableName = "Medicine")
public class Medicine {
    private int medID;
    private String name;
    private int icon;
    private String strength;
    private String medType;
    private boolean isActive;
    private String instructions;
    private String reason;
    private int refillReminder;
    private int numOfPills;
    private List<Long> times;

    public Medicine() {
    }

    public Medicine(int medID, String name, int icon, String strength, String medType,
                    boolean isActive, String instructions, String reason, int refillReminder, int numOfPills, List<Long> times) {
        this.medID = medID;
        this.name = name;
        this.icon = icon;
        this.strength = strength;
        this.medType = medType;
        this.isActive = isActive;
        this.instructions = instructions;
        this.reason = reason;
        this.refillReminder = refillReminder;
        this.numOfPills = numOfPills;
        this.times = times;
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getMedType() {
        return medType;
    }

    public void setMedType(String medType) {
        this.medType = medType;
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
