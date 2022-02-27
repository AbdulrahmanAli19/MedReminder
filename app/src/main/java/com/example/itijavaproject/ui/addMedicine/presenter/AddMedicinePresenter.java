package com.example.itijavaproject.ui.addMedicine.presenter;

import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.RepositoryInterface;
import com.example.itijavaproject.ui.addMedicine.view.AddMedicineInterface;

public class AddMedicinePresenter implements AddMedicinePresenterInterface {

    private RepositoryInterface repo;

    private  AddMedicineInterface addMedicineInterface;


    public AddMedicinePresenter(RepositoryInterface repo,
                                AddMedicineInterface addMedicineInterface) {
        this.repo = repo;
        this.addMedicineInterface=addMedicineInterface;
    }

    @Override
    public void addMedicine(Medicine medicine) {
        repo.addMedicine(medicine);

    }

    @Override
    public void editMedicine(Medicine medicine) {
        repo.editMedicine(medicine);

    }
}
