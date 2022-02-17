package com.example.itijavaproject.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

@Dao
public interface MedicineDao {
    @Insert
    void insertMedicine(Medicine medicine);

    @Delete
    void deleteMedicine(Medicine medicine);

    @Update
    void updateMedicine(Medicine medicine);

    @Query("SELECT * FROM Medicine")
    LiveData<List<Medicine>> getAllMedicines();

    @Query("SELECT * FROM Medicine WHERE isActive =true")
    LiveData<List<Medicine>> getActiveMeds();


    @Query("SELECT * FROM Medicine WHERE isActive =false")
    LiveData<List<Medicine>> getInActiveMeds();

}
