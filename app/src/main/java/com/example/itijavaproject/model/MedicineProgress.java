package com.example.itijavaproject.model;

import java.util.List;

public class MedicineProgress {

    private int medID;
    private Medicine medicine;
    private int numOfPills;
    private List<Long> times;

    public MedicineProgress() {
    }

    public MedicineProgress(int medID, Medicine medicine, int numOfPills, List<Long> times) {
        this.medID = medID;
        this.medicine = medicine;
        this.numOfPills = numOfPills;
        this.times = times;
    }

    public int getMedID() {
        return medID;
    }

    public void setMedID(int medID) {
        this.medID = medID;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
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
