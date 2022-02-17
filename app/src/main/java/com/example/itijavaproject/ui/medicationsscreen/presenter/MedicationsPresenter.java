package com.example.itijavaproject.ui.medicationsscreen.presenter;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;

import com.example.itijavaproject.data.db.ConcretLocalSource;
import com.example.itijavaproject.pojo.repo.Repository;
import com.example.itijavaproject.ui.medicationsscreen.view.MedicationViewInterface;

public class MedicationsPresenter implements MedicationspresenterInterface {
    MedicationViewInterface medicationViewInterface;
    Repository repository;

    public MedicationsPresenter(MedicationViewInterface medicationViewInterface, Context context) {
        this.medicationViewInterface = medicationViewInterface;
        repository=Repository.getInstance(ConcretLocalSource.getInstance(context),context);
    }

    @Override
    public void getActiveMed(LifecycleOwner owner) {
        repository.showActiveMed();
    }

    @Override
    public void getInactiveMed(LifecycleOwner owner) {
        repository.showInactiveMed();
    }
}
