package com.example.itijavaproject.ui.addMedicine.presenter;

import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.RepositoryInterface;

public class addMedicinePresenter implements AddMedicinePresenterInterface{
   private RepositoryInterface repo;

    public addMedicinePresenter(RepositoryInterface repo, AddMedicinePresenterInterface addMedicinePresenterInterface) {
        this.repo = repo;
        this.addMedicinePresenterInterface = addMedicinePresenterInterface;
    }

    private AddMedicinePresenterInterface addMedicinePresenterInterface;
    @Override
    public void addMedicine(Medicine medicine) {
        repo.addMedicine(medicine);

    }
}
