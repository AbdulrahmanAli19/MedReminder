package com.example.itijavaproject.ui.homescreen.view;

import androidx.annotation.NonNull;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public interface HomeCommunicator {

    void onDateChange(@NonNull MaterialCalendarView widget,
                      @NonNull CalendarDay date, boolean selected);
}
