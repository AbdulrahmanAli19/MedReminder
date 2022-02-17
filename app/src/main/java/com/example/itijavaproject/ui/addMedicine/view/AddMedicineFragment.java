package com.example.itijavaproject.ui.addMedicine.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.itijavaproject.MainActivity;
import com.example.itijavaproject.R;
import com.example.itijavaproject.data.db.DatabaseAccess;
import com.example.itijavaproject.databinding.FragmentAddMedicineBinding;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddMedicineFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private FragmentAddMedicineBinding binding;
    int countAmount;
    int countFrequency ;
    Calendar myCalender;
    Calendar myCalenderTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void showHourPicker() {
        myCalenderTime = Calendar.getInstance();
        int hour = myCalenderTime.get(Calendar.HOUR_OF_DAY);
        int minute = myCalenderTime.get(Calendar.MINUTE);
        //int t=myCalender.get(Calendar.AM_PM);

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalenderTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalenderTime.set(Calendar.MINUTE, minute);
                    binding.txtTime.setText(hourOfDay + ":" + minute);

                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                myTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Choose hour:");

        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    public void showDatePicker() {
        myCalender = Calendar.getInstance();
        int year = myCalender.get(Calendar.YEAR);
        int month = myCalender.get(Calendar.MONTH);
        int day = myCalender.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                if (view.isShown()) {
                    month = month + 1;
                    String date = day + "/" + month + "/" + year;
                    binding.startDateTxt.setText(date);
                }
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                myDateListener, year, month, day);
        datePickerDialog.setTitle("Choose start date:");

        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();
    }

    private Medicine createMedicine() {
        Medicine medicine = new Medicine();
        medicine.setName(binding.medName.getEditableText().toString());
        medicine.setStrength(binding.strength.getEditableText().toString());
        //medicine.setIconType(binding);
        medicine.setStartDate(myCalender.getTimeInMillis());
        medicine.setDuration(binding.durationMenu.getSelectedItem().toString());
        medicine.setNumOfPills(Integer.parseInt(binding.txtAmount.getText().toString()));
        medicine.setFrequencyPerDay(Integer.parseInt(binding.txtFrequence.getText().toString()));
        medicine.setIsRefillReminder(binding.refillSwitch.isClickable());

        //medicine.setTimes(binding.txtTime.toString()));


        return medicine;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
        binding.timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHourPicker();
            }
        });
        binding.amountAddBtn.setOnClickListener(v -> {
            binding.txtAmount.setText(String.valueOf(countAmount++));
        });
        binding.amountMinusBtn.setOnClickListener(v -> {
            binding.txtAmount.setText(String.valueOf(countAmount--));
        });
        binding.frequenceAddBtn.setOnClickListener(v -> {
            binding.txtFrequence.setText(String.valueOf(countFrequency++));
        });
        binding.frequenceMinusBtn.setOnClickListener(v -> {
            binding.txtFrequence.setText(String.valueOf(countFrequency--));
        });
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(() -> databaseAccess.medicineDao().insertMedicine(createMedicine())).start();
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddMedicineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }
}