package com.example.itijavaproject.ui.medicationsscreen.presenter;

import androidx.lifecycle.LifecycleOwner;

public interface MedicationspresenterInterface {
public void getActiveMed();
public void getInactiveMed();
public void getAll(LifecycleOwner owner);
}
