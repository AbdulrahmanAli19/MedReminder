package com.example.itijavaproject.ui.medicationsscreen.presenter;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.example.itijavaproject.data.db.ConcretLocalSource;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.Repository;
import com.example.itijavaproject.pojo.repo.RepositoryInterface;
import com.example.itijavaproject.ui.medicationsscreen.view.MedicationViewInterface;

import java.util.List;

public class MedicationsPresenter implements MedicationspresenterInterface {
    MedicationViewInterface medicationViewInterface;
    RepositoryInterface repository;
    List<Medicine> medicineList;

    public MedicationsPresenter(MedicationViewInterface medicationViewInterface,RepositoryInterface repository) {
        this.medicationViewInterface = medicationViewInterface;
        this.repository=repository;
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
