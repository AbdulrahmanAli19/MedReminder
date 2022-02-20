package com.example.itijavaproject.pojo.repo;

import androidx.lifecycle.LiveData;

import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

public interface RepositoryInterface {
    LiveData<List<Medicine>> getStoredMedicine();
    void getSelectedMedicine(Medicine medicine);
    LiveData<List<Medicine>> getAllMedicines();     //add parameter

    LiveData<List<Medicine>> showActiveMed();

    LiveData<List<Medicine>> showInactiveMed();

    LiveData<List<Medicine>> getSelectedDateMedicines(Long date);

    void addMedicine(Medicine medicine);

    void deleteMedicine(Medicine medicine);

    void editMedicine(Medicine medicine);

}
