package com.example.itijavaproject.ui.medicationsscreen.presenter;

import androidx.lifecycle.LifecycleOwner;

public interface MedicationsPresenterInterface {
    void getActiveMed();

    void getInactiveMed();

    void getAll(LifecycleOwner owner);
}
