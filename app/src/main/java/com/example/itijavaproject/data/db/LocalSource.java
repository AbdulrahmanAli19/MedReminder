package com.example.itijavaproject.data.db;

import androidx.lifecycle.LiveData;


import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;
import io.reactivex.Maybe;


public interface LocalSource {
    LiveData<List<Medicine>> getStoredMedicine();

    LiveData<List<Medicine>> getActive();

    LiveData<List<Medicine>> getInactive();

    //firebase
    LiveData<Medicine> getAllMedicine();

    void addMedicine(Medicine medicine);

    void deleteMedicine(Medicine medicine);

    void editMedicine(Medicine medicine);

    Maybe<List<Medicine>> getSelectedDayMedicines(Long selectedDate);
}
