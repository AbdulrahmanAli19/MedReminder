package com.example.itijavaproject.ui.addMedicine.presenter;

import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.RepositoryInterface;

public class AddMedicinePresenter implements AddMedicinePresenterInterface {

    private RepositoryInterface repo;

    private AddMedicinePresenterInterface addMedicinePresenterInterface;


    public AddMedicinePresenter(RepositoryInterface repo,
                                AddMedicinePresenterInterface addMedicinePresenterInterface) {
        this.repo = repo;
        this.addMedicinePresenterInterface = addMedicinePresenterInterface;
    }

    @Override
    public void addMedicine(Medicine medicine) {
        repo.addMedicine(medicine);

    }
}
