package com.example.itijavaproject.pojo.repo;

import androidx.lifecycle.LiveData;

import com.example.itijavaproject.data.db.LocalSource;
import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

public class Repository implements RepositoryInterface {
    private static Repository repository;
    private LocalSource localSource;

    private Repository() {
    }

    public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    @Override
    public LiveData<List<Medicine>> getStoredMedicine() {
        return localSource.getStoredMedicine();
    }

    @Override
    public void getAllMedicines() {

    }

    @Override
    public void addMedicine(Medicine medicine) {
        localSource.addMedicine(medicine);

    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        localSource.deleteMedicine(medicine);

    }

    @Override
    public void editMedicine(Medicine medicine) {
        localSource .editMedicine(medicine);   }
}
