package com.example.itijavaproject.data.db;

import androidx.lifecycle.LiveData;


import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

public interface LocalSource {
    LiveData<List<Medicine>> getStoredMedicine();
    LiveData<List<Medicine>>getActive();
    LiveData<List<Medicine>>getInactive();

    void addMedicine(Medicine medicine);
    void deleteMedicine(Medicine medicine);
    void editMedicine(Medicine medicine);
}
