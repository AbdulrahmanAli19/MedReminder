package com.example.itijavaproject.ui.homescreen.presenter;


import com.example.itijavaproject.pojo.model.Medicine;

public interface HomePresenterInterface {
    void getSelectedDateMedicines(Long date);

    void updateMed(Medicine medicine);

    void deleteMed(Medicine medicine);
}
