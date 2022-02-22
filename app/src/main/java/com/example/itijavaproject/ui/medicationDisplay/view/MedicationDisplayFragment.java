package com.example.itijavaproject.ui.medicationDisplay.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.itijavaproject.R;
import com.example.itijavaproject.data.db.DatabaseAccess;
import com.example.itijavaproject.databinding.FragmentMedicationDisplayBinding;
import com.example.itijavaproject.pojo.model.Medicine;

import com.example.itijavaproject.ui.medicationDisplay.presenter.MedicineDisplayPresenterInterface;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class MedicationDisplayFragment extends Fragment implements MedicineDisplayInterface {
    private static final String TAG = "MedicationDisplayFragme";
    private FragmentMedicationDisplayBinding binding;
    private MedicineDisplayPresenterInterface presenter;
    NavController navController;
    NavDirections directions;
    DatabaseAccess databaseAccess;

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Medicine medicine = MedicationDisplayFragmentArgs.fromBundle(getArguments()).getObjOfMeds();
        if (medicine.getIconType().equals(getActivity().getApplicationContext().getString(R.string.pill))) {
            binding.drugIcon.setImageDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.ic_pill));
        } else if (medicine.getIconType().equals(getActivity().getApplicationContext().getString(R.string.bottle))) {
            binding.drugIcon.setImageDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.ic_bottle_pill));
        } else if (medicine.getIconType().equals(getActivity().getApplicationContext().getString(R.string.drop))) {
            binding.drugIcon.setImageDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.ic_medicine));
        } else if (medicine.getIconType().equals(getActivity().getApplicationContext().getString(R.string.injection))) {
            binding.drugIcon.setImageDrawable(getActivity().getApplicationContext().getResources().getDrawable(R.drawable.ic_injection__1_));
        }
        binding.drugName.setText(medicine.getName());
        binding.noDrugStrength.setText(String.valueOf(medicine.getNoOfStrength()));
        binding.drugStrength.setText(medicine.getStrength());
        binding.txtGetDuration.setText(medicine.getDuration());
        binding.howUse.setText(medicine.getInstructions());
//        LocalDate date = LocalDate.ofEpochDay(new Long(medicine.getEndDate()));
//        binding.txtGetDuration.setText(date.getDayOfMonth()+"/"+date.getMonth()+"/"+date.getYear());

        Log.i(TAG, "onViewCreated: " + medicine.getDuration());
        binding.noPills.append("No.of Pills: " + medicine.getNumOfPills());
        for (int i = 0; i < medicine.getTimes().size(); i++) {
            long millisecondsSinceEpoch = medicine.getTimes().get(i);
            Instant instant = Instant.ofEpochMilli(millisecondsSinceEpoch);
            ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
            String output = formatter.format(zdt);
            binding.txtTimes.append(output + " take 1 pill(s) \n");

        }
        if (medicine.isActive() == true) {
            binding.suspendBtn.setText("SUSPEND");
            binding.suspendBtn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      binding.suspendBtn.setText("ACTIVE");
                      medicine.setActive(false);
                      new Thread(new Runnable() {
                          @Override
                          public void run() {
                              databaseAccess.medicineDao().updateMedicine(medicine);

                          }
                      }).start();
                  }
              }
            );

        } else {
            binding.suspendBtn.setText("ACTIVE");
            binding.suspendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.suspendBtn.setText("SUSPEND");
                    medicine.setActive(true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            databaseAccess.medicineDao().updateMedicine(medicine);

                        }
                    }).start();
                }
            });

        }
        binding.refillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        binding.addDoseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController(view);
                directions = MedicationDisplayFragmentDirections.actionMedicationDisplayFragmentToAddMedicineFragment2(medicine);
                navController.navigate(directions);

            }
        });
        databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        databaseAccess.medicineDao().deleteMedicine(medicine);

                    }
                }).start();
                navController = Navigation.findNavController(view);
                directions = MedicationDisplayFragmentDirections.actionMedicationDisplayFragmentToMedicationsFragment3();
                navController.navigate(directions);

            }
        });

        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController = Navigation.findNavController(view);
                directions = MedicationDisplayFragmentDirections.actionMedicationDisplayFragmentToAddMedicineFragment2(medicine);
                navController.navigate(directions);
            }
        });
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