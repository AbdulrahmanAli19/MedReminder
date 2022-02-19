package com.example.itijavaproject.ui.medicationDisplay.presenter;

import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.RepositoryInterface;
import com.example.itijavaproject.ui.medicationDisplay.view.MedicineDisplayInterface;

public class MedicationDisplayPresenter implements MedicineDisplayPresenterInterface{
    private RepositoryInterface repo;
    private MedicineDisplayInterface medicineDisplayInterface;

    public MedicationDisplayPresenter(RepositoryInterface repo, MedicineDisplayInterface medicineDisplayInterface) {
        this.repo = repo;
        this.medicineDisplayInterface = medicineDisplayInterface;
    }

    @Override
    public void getMedicine(Medicine medicine) {
        repo.getSelectedMedicine(medicine);
    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        repo.deleteMedicine(medicine);

    }
    @Override
    public void editMedicine(Medicine medicine) {
        repo.editMedicine(medicine);
    }
}
