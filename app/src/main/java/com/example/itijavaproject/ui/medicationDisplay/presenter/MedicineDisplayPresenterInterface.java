package com.example.itijavaproject.ui.medicationDisplay.presenter;

import com.example.itijavaproject.pojo.model.Medicine;

public interface MedicineDisplayPresenterInterface {
    void getMedicine(Medicine medicine);
    void deleteMedicine(Medicine medicine);
    void editMedicine(Medicine medicine);
}
