package com.example.itijavaproject.ui.homescreen.view;

import androidx.lifecycle.LiveData;

import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

import io.reactivex.Maybe;

public interface HomeFragInterface {
    void getSelectedDateMedicines(Maybe<List<Medicine>> medicines);
}
