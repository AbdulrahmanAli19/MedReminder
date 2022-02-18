package com.example.itijavaproject.ui.medicationDisplay.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.itijavaproject.data.db.DatabaseAccess;
import com.example.itijavaproject.databinding.FragmentMedicationDisplayBinding;
import com.example.itijavaproject.pojo.model.Medicine;


public class MedicationDisplayFragment extends Fragment {

    private FragmentMedicationDisplayBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMedicationDisplayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    private Medicine getMedicine() {
        Medicine medicine = new Medicine();
        binding.drugName.setText(medicine.getName());
        //binding.drugIcon
        binding.noPills.setText(String.valueOf(medicine.getNumOfPills()));
        binding.drugStrength.setText(medicine.getStrength());
       // binding.txtGetDuration.setText(medicine.getStartDate()+" , "+medicine.getDuration());
        //refill
        binding.refillReminder.setText(medicine.getFrequencyPerDay()+" time per day");

        //medicine.setTimes(binding.txtTime.toString()));

        return medicine;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMedicine();
        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getActivity().getApplicationContext());
        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //databaseAccess.medicineDao().deleteMedicine();
            }
        });
    }
}