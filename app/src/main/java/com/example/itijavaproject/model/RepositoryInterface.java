package com.example.itijavaproject.model;

import androidx.lifecycle.LiveData;

public interface RepositoryInterface {
    LiveData<Medicine> grtAllMedicine();
    void getAllMedicines();     //add parameter
    void addMedicine(Medicine medicine);
    void deleteMedicine(Medicine medicine);
    void editMedicine(Medicine medicine);


}
