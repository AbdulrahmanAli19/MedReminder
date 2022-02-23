package com.example.itijavaproject.data.network;

import androidx.lifecycle.LiveData;

import com.example.itijavaproject.pojo.model.Medicine;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Network implements NetworkInterface{

    private FirebaseDatabase firebaseDatabase;


    @Override
    public LiveData<List<Medicine>> getActiveRemote() {
        return null;
    }

    @Override
    public LiveData<List<Medicine>> getInactiveRemote() {
        return null;
    }

    @Override
    public void addMedicineRemote(Medicine medicine) {

    }

    @Override
    public void deleteMedicineRemote(Medicine medicine) {

    }

    @Override
    public void editMedicineRemote(Medicine medicine) {

    }
}
