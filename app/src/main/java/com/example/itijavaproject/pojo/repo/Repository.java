package com.example.itijavaproject.pojo.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.itijavaproject.data.db.LocalSource;
import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

public class Repository implements RepositoryInterface {
    private static Repository repository;
    private LocalSource localSource;
    private Context context;

    public Repository(LocalSource localSource, Context context) {
        this.localSource = localSource;
        this.context = context;
    }

    public static Repository getInstance(LocalSource localSource, Context context) {
        if (repository == null) {
            repository = new Repository(localSource,context);
        }
        return repository;
    }

    @Override
    public LiveData<List<Medicine>> getStoredMedicine() {
        return localSource.getStoredMedicine();
    }

    @Override
    public void getSelectedMedicine(Medicine medicine) {

    }

    @Override
    public LiveData<List<Medicine>> getAllMedicines() {
        return null;
    }

    @Override
    public LiveData<List<Medicine>> showActiveMed() {
        return localSource.getActive();
    }

    @Override
    public LiveData<List<Medicine>> showInactiveMed() {
        return localSource.getInactive();
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
