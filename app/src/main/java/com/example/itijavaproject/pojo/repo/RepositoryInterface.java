package com.example.itijavaproject.pojo.repo;

import androidx.lifecycle.LiveData;

import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

public interface RepositoryInterface {
    LiveData<List<Medicine>> getStoredMedicine();

    void getAllMedicines();     //add parameter

    void addMedicine(Medicine medicine);

    void deleteMedicine(Medicine medicine);

    void editMedicine(Medicine medicine);

}
