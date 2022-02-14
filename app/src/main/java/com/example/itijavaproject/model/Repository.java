package com.example.itijavaproject.model;

import androidx.lifecycle.LiveData;

public class Repository implements RepositoryInterface{

    private static Repository repository;

    private Repository() {
    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    @Override
    public LiveData<Medicine> grtAllMedicine() {
        return null;
    }

    @Override
    public void getAllMedicines() {

    }

    @Override
    public void addMedicine(Medicine medicine) {

    }

    @Override
    public void deleteMedicine(Medicine medicine) {

    }

    @Override
    public void editMedicine(Medicine medicine) {

    }
}
