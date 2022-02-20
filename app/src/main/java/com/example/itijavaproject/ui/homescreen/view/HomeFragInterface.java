package com.example.itijavaproject.ui.homescreen.view;

import androidx.lifecycle.LiveData;

import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

public interface HomeFragInterface {
    void getSelectedDateMedicines(LiveData<List<Medicine>> medicineList);
}
