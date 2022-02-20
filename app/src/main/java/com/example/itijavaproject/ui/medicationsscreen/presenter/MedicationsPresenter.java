package com.example.itijavaproject.ui.medicationsscreen.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.example.itijavaproject.pojo.repo.RepositoryInterface;
import com.example.itijavaproject.ui.medicationsscreen.view.MedicationViewInterface;

public class MedicationsPresenter implements MedicationsPresenterInterface {

    private final MedicationViewInterface medicationViewInterface;
    private final RepositoryInterface repository;

    public MedicationsPresenter(MedicationViewInterface medicationViewInterface,
                                RepositoryInterface repository) {
        this.medicationViewInterface = medicationViewInterface;
        this.repository = repository;
    }

    @Override
    public void getActiveMed() {
        medicationViewInterface.displayActiveMeds(repository.showActiveMed());
    }

    @Override
    public void getInactiveMed() {
        medicationViewInterface.displayInactiveMeds(repository.showInactiveMed());
    }

    @Override
    public void getAll(LifecycleOwner owner) {
        repository.getAllMedicines();
    }
}
