package com.example.itijavaproject.ui.medicationDisplay.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.itijavaproject.R;
import com.example.itijavaproject.data.db.DatabaseAccess;
import com.example.itijavaproject.databinding.FragmentMedicationDisplayBinding;
import com.example.itijavaproject.pojo.model.Medicine;

import com.example.itijavaproject.pojo.model.User;
import com.example.itijavaproject.ui.medicationDisplay.presenter.MedicineDisplayPresenterInterface;
import com.example.itijavaproject.workers.WorkerUtil;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.threeten.bp.LocalDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class MedicationDisplayFragment extends Fragment implements MedicineDisplayInterface {
    private static final String TAG = "MedicationDisplayFragme";
    private FragmentMedicationDisplayBinding binding;
    private MedicineDisplayPresenterInterface presenter;
    NavController navController;
    NavDirections directions;
    DatabaseAccess databaseAccess;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("medicine");

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
        navController = Navigation.findNavController(view);
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
        Date startDate=new Date(medicine.getStartDate());
        Date endDate=new Date(medicine.getEndDate());
        Long duration=endDate.getTime()-startDate.getTime();
        LocalDate localDate =LocalDate.ofEpochDay(duration);
        binding.txtGetDuration.setText(medicine.getDuration()+" ,take for "+localDate.getDayOfMonth()+" days");
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

                      databaseReference.child(FirebaseAuth.getInstance().getUid())
                              .addListenerForSingleValueEvent(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                                      //User user=snapshot.getValue(null);
                                      String mID=snapshot.getKey();
                                      databaseReference.child(mID).removeValue();
                                      Toast.makeText(getContext(), "saved in firebase", Toast.LENGTH_SHORT).show();
                                  }

                                  @Override
                                  public void onCancelled(@NonNull DatabaseError error) {
                                      Toast.makeText(getContext(), "faild", Toast.LENGTH_SHORT).show();

                                  }
                              });
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
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View dialogLayout = inflater.inflate(R.layout.custom_row_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogLayout);
                TextView Refill =dialogLayout.findViewById(R.id.txtmsgRefill);
                TextView addText=dialogLayout.findViewById(R.id.txtadd);
                TextView numpill=dialogLayout.findViewById(R.id.numPills);
                TextView txtmsg=dialogLayout.findViewById(R.id.txtmsg);
                final EditText AddEdit = (EditText) dialogLayout.findViewById(R.id.editRefill);
                numpill.append("you have "+medicine.getNumOfPills());
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (AddEdit.getText().toString().isEmpty()) {
                            Snackbar.make(getContext(),getView(),"please enter number of pills",Snackbar.LENGTH_LONG).show();
                        } else {
                            medicine.setNumOfPills(Integer.parseInt(AddEdit.getText().toString()));
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    databaseAccess.medicineDao().updateMedicine(medicine);
                                }
                            }).start();
                        }
                    }
                });
                builder.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog customAlertDialog = builder.create();
                customAlertDialog.show();
            }
        });
        WorkerUtil workerUtil=new WorkerUtil();
        binding.addDoseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                workerUtil.createNotification(getContext());
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
                navController.popBackStack();
            }
        });

        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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