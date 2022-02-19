package com.example.itijavaproject.ui.medicationDisplay.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.itijavaproject.data.db.DatabaseAccess;
import com.example.itijavaproject.databinding.FragmentMedicationDisplayBinding;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.ui.medicationDisplay.presenter.MedicineDisplayPresenterInterface;
import com.example.itijavaproject.ui.medicationsscreen.view.InactiveMedicationAdapter;
import com.example.itijavaproject.ui.medicationsscreen.view.MedicationsFragment;
import com.google.type.DateTime;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class MedicationDisplayFragment extends Fragment implements MedicineDisplayInterface {
    private static final String TAG = "MedicationDisplayFragme";
    private FragmentMedicationDisplayBinding binding;
    private MedicineDisplayPresenterInterface presenter;

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

    List<Long> listTime = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Medicine medicine= MedicationDisplayFragmentArgs.fromBundle(getArguments()).getObjOfMeds();
        // binding.drugIcon.setImageResource(Integer.parseInt(medicine.getIconType()));
        binding.drugName.setText(medicine.getName());
        binding.noDrugStrength.setText(String.valueOf(medicine.getNoOfStrength()));
        binding.drugStrength.setText(medicine.getStrength());
        binding.txtGetDuration.setText(medicine.getDuration());
        binding.noPills.setText(String.valueOf(medicine.getNumOfPills()));
        for(int i=0;i<medicine.getTimes().size();i++)
        {
            long millisecondsSinceEpoch = medicine.getTimes().get(i);
            Instant instant = Instant.ofEpochMilli ( millisecondsSinceEpoch );
            ZonedDateTime zdt = ZonedDateTime.ofInstant ( instant , ZoneOffset.UTC );
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "h:mm a" );
            String output = formatter.format ( zdt );
            binding.txtTimes.append(output+" take 1 pill(s) \n");
            Log.i(TAG, "onViewCreated: "+output);


        }


//        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getActivity().getApplicationContext());
//        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //databaseAccess.medicineDao().deleteMedicine();
//            }
//        });
    }
    @Override
    public void viewMedicine(Medicine medicine) {

    }
    @Override
    public void editMedicine(Medicine medicine) {
        presenter.editMedicine(medicine);

    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        presenter.deleteMedicine(medicine);

    }
}