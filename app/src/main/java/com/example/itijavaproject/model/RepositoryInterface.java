package com.example.itijavaproject.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface RepositoryInterface {
    LiveData<List<Medicine>> getStoredMedicine();
    void getAllMedicines();     //add parameter
    void addMedicine(Medicine medicine);
    void deleteMedicine(Medicine medicine);
    void editMedicine(Medicine medicine);


}
