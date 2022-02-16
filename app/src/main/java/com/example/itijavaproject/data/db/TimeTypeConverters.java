package com.example.itijavaproject.data.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TimeTypeConverters {

    private Gson gson;

    @TypeConverter
    public String timeToString(List<Long> times) {
        gson = new Gson();
        Type type = new TypeToken<List<Long>>() {
        }.getType();
        String json = gson.toJson(times, type);
        return json;
    }

    @TypeConverter
    public List<Long> stringToTime(String json) {
        gson = new Gson();
        Type type = new TypeToken<List<Long>>() {
        }.getType();
        List<Long> time = gson.fromJson(json, type);
        return time;
    }
}
