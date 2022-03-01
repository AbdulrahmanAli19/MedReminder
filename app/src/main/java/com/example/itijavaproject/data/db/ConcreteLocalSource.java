package com.example.itijavaproject.data.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

import io.reactivex.Maybe;

public class ConcreteLocalSource implements LocalSource {

    private final MedicineDao medicineDao;
    private static ConcreteLocalSource concreteLocalSource = null;

    private ConcreteLocalSource(Context context) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context.getApplicationContext());
        medicineDao = databaseAccess.medicineDao();
    }

    public static ConcreteLocalSource getInstance(Context context) {
        if (concreteLocalSource == null) {
            concreteLocalSource = new ConcreteLocalSource(context);
        }
        return concreteLocalSource;
    }

    @Override
    public LiveData<List<Medicine>> getStoredMedicine() {
        return medicineDao.getAllMedicines();
    }

    @Override
    public LiveData<List<Medicine>> getActive() {
        return medicineDao.getActiveMeds();
    }

    @Override
    public LiveData<List<Medicine>> getInactive() {
        return medicineDao.getInActiveMeds();
    }

    @Override
    public LiveData<Medicine> getAllMedicine() {
        return null;
    }

    @Override
    public Maybe<List<Medicine>> getSelectedDayMedicines(Long selectedDate) {
        return medicineDao.getCurrentDayMedicines(selectedDate);
    }

    @Override
    public Maybe<Medicine> getMedById(String id) {
        return medicineDao.getMedById(id);
    }

    @Override
    public void addMedicine(Medicine medicine) {
        new Thread(() -> medicineDao.insertMedicine(medicine)).start();

    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        new Thread(() -> medicineDao.deleteMedicine(medicine)).start();
    }

    @Override
    public void editMedicine(Medicine medicine) {
        new Thread(() -> medicineDao.updateMedicine(medicine)).start();

    }
}