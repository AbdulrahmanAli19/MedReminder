package com.example.itijavaproject.data.network;

import androidx.lifecycle.LiveData;

import com.example.itijavaproject.pojo.model.Medicine;

public interface NetworkInterface {
    //firebase
    LiveData<Medicine> getAllMedicine();

}
