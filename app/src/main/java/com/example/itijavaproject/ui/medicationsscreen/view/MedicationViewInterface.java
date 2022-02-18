package com.example.itijavaproject.ui.medicationsscreen.view;

import androidx.lifecycle.LiveData;

import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

public interface MedicationViewInterface {
//    void displayActiveMeds(List<Medicine> activeMeds);
void displayActiveMeds(LiveData<List<Medicine>> med);

    void displayInactiveMeds(LiveData<List<Medicine>> med);
}
