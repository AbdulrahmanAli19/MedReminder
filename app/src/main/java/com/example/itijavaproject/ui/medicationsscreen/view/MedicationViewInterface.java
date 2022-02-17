package com.example.itijavaproject.ui.medicationsscreen.view;

import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;
public interface MedicationViewInterface {
void displayActiveMeds(List<Medicine>activeMeds);
void displayInactiveMeds(List<Medicine>inactiveMeds);
}
