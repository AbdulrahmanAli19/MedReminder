package com.example.itijavaproject.data.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.itijavaproject.pojo.model.Medicine;

@Database(entities = Medicine.class, version = 1)
@TypeConverters(TimeTypeConverters.class)
public abstract class DatabaseAccess extends RoomDatabase {

    private final static String DB_NAME = "MED_APP";

    public abstract MedicineDao medicineDao();

    private static DatabaseAccess db = null;

    private static synchronized DatabaseAccess getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context, DatabaseAccess.class, DB_NAME).build();
        }
        return db;
    }
}
