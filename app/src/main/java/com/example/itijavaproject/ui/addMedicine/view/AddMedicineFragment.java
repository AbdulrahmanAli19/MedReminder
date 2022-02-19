package com.example.itijavaproject.ui.addMedicine.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.itijavaproject.R;
import com.example.itijavaproject.data.db.DatabaseAccess;
import com.example.itijavaproject.databinding.FragmentAddMedicineBinding;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.ui.addMedicine.presenter.AddMedicinePresenterInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddMedicineFragment extends Fragment implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener, AddMedicineInterface, View.OnClickListener {

    private FragmentAddMedicineBinding binding;
    int countAmount;
    int countFrequency;
    Calendar myCalender;
    Calendar myCalenderTime = Calendar.getInstance();
    AddMedicinePresenterInterface presenterInterface;
    NavController navController;
    NavDirections directions;
    Medicine medicine = new Medicine();
    List<Long> listTime = new ArrayList<>();
    String s;

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

                   listTime.add(myCalenderTime.getTimeInMillis());

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
         medicine = new Medicine();
        medicine.setName(binding.medName.getEditableText().toString());
        medicine.setStrength(binding.strength.getSelectedItem().toString());
        medicine.setIconType(s);
        medicine.setStartDate(myCalender.getTimeInMillis());
        medicine.setDuration(binding.durationMenu.getSelectedItem().toString());
        medicine.setNumOfPills(Integer.parseInt(binding.txtAmount.getText().toString()));
        medicine.setFrequencyPerDay(Integer.parseInt(binding.txtFrequence.getText().toString()));
        medicine.setNoOfStrength(Integer.parseInt(binding.noOfStrength.getEditableText().toString()));
        medicine.setTimes(listTime);
        medicine.setIsRefillReminder(binding.refillSwitch.isChecked());

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
            if(countAmount>0){ binding.txtAmount.setText(String.valueOf(countAmount--));}

        });
        binding.frequenceAddBtn.setOnClickListener(v -> {
            binding.txtFrequence.setText(String.valueOf(countFrequency++));
        });
        binding.frequenceMinusBtn.setOnClickListener(v -> {
            if(countFrequency>0){binding.txtFrequence.setText(String.valueOf(countFrequency--));
            }
        });
        binding.pillBtn.setOnClickListener(this);
        binding.bottleBtn.setOnClickListener(this);
        binding.dropBtn.setOnClickListener(this);
        binding.injectionBtn.setOnClickListener(this);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
        navController = Navigation.findNavController(view);
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(() -> databaseAccess.medicineDao().insertMedicine(createMedicine())).start();
                directions=AddMedicineFragmentDirections.actionAddMedicineFragmentToMedicationsFragment2();
                navController.navigate(directions);
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

    @Override
    public void addMedicine(Medicine medicine) {
        presenterInterface.addMedicine(medicine);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.pillBtn:
                s = binding.pillBtn.getContentDescription().toString();
                binding.pillBtn.setBackgroundResource(R.color.background);
                break;
            case R.id.injectionBtn:
                s = binding.injectionBtn.getContentDescription().toString();
                binding.injectionBtn.setBackgroundResource(R.color.background);
                break;
            case R.id.dropBtn:
                s = binding.dropBtn.getContentDescription().toString();
                binding.dropBtn.setBackgroundResource(R.color.background);
                break;
            case R.id.bottleBtn:
                s = binding.bottleBtn.getContentDescription().toString();
                binding.bottleBtn.setBackgroundResource(R.color.background);
                break;

        }
    }
}