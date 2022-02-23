package com.example.itijavaproject.data.network;

import androidx.lifecycle.LiveData;

import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

public interface NetworkInterface {
    //firebase
//    LiveData<Medicine> getAllMedicine();

    LiveData<List<Medicine>> getActiveRemote();

    LiveData<List<Medicine>> getInactiveRemote();

    void addMedicineRemote(Medicine medicine);

    void deleteMedicineRemote(Medicine medicine);

    void editMedicineRemote(Medicine medicine);


}
