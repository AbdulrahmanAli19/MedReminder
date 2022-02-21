package com.example.itijavaproject.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

import io.reactivex.Maybe;

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

    @Query("SELECT * FROM Medicine WHERE isActive =1")
    LiveData<List<Medicine>> getActiveMeds();

    @Query("SELECT * FROM Medicine WHERE isActive = 0")
    LiveData<List<Medicine>> getInActiveMeds();

    @Query("SELECT * FROM MEDICINE WHERE :selectedDate BETWEEN startDate AND endDate")
    Maybe<List<Medicine>> getCurrentDayMedicines(long selectedDate);

}
