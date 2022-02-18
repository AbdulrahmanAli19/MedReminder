package com.example.itijavaproject.data.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

public class ConcretLocalSource implements LocalSource{
    MedicineDao medicineDao;
    private LiveData<List<Medicine>> stroeInactiveMeds,stroeActiveMeds ,storeAllData;
    private static ConcretLocalSource conc = null;
    private ConcretLocalSource(Context context) {
        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(context.getApplicationContext());
        medicineDao =databaseAccess.medicineDao();
        stroeActiveMeds=medicineDao.getActiveMeds();
        stroeInactiveMeds=medicineDao.getInActiveMeds();
        storeAllData=medicineDao.getAllMedicines();
    }
    public static ConcretLocalSource getInstance(Context context){
        if (conc==null)
        {
            conc=new ConcretLocalSource(context);
        }
        return conc;
    }
    @Override
    public LiveData<List<Medicine>> getStoredMedicine() {
        return storeAllData;
    }

    @Override
    public LiveData<List<Medicine>> getActive() {
        return stroeActiveMeds;
    }

    @Override
    public LiveData<List<Medicine>> getInactive() {
        return stroeInactiveMeds;
    }

    @Override
    public LiveData<Medicine> getAllMedicine() {
        return null;
    }

    @Override
    public void addMedicine(Medicine medicine) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                medicineDao.insertMedicine(medicine);
//            }
//        }).start();

    }

    @Override
    public void deleteMedicine(Medicine medicine) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                medicineDao.deleteMedicine(medicine);
//            }
//        }).start();

    }

    @Override
    public void editMedicine(Medicine medicine) {

    }
}
