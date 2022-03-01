package com.example.itijavaproject.ui.addMedicine.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.itijavaproject.R;
import com.example.itijavaproject.data.db.ConcreteLocalSource;
import com.example.itijavaproject.data.db.DatabaseAccess;
import com.example.itijavaproject.databinding.FragmentAddMedicineBinding;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.model.User;
import com.example.itijavaproject.pojo.repo.Repository;
import com.example.itijavaproject.ui.addMedicine.presenter.AddMedicinePresenter;
import com.example.itijavaproject.ui.addMedicine.presenter.AddMedicinePresenterInterface;
import com.example.itijavaproject.ui.medicationDisplay.presenter.MedicationDisplayPresenter;
import com.example.itijavaproject.workers.AddMedReminder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddMedicineFragment extends Fragment implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener, AddMedicineInterface, View.OnClickListener {
    private static final String TAG = "AddMedicineFragment";
    private FragmentAddMedicineBinding binding;
    private AddMedicinePresenterInterface presenterInterface;
    private NavController navController;
    private NavDirections directions;
    Calendar startCalender, endCalender;
    String startDate;
    String endDate;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("users");
    Medicine medicine = new Medicine();
    List<Long> listTime = new ArrayList<>();
    String s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showHourPicker() {
        final Calendar myCalenderTime = Calendar.getInstance();
        int hourOfDay =myCalenderTime.get(Calendar.HOUR_OF_DAY);
        int minute = myCalenderTime.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalenderTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalenderTime.set(Calendar.MINUTE, minute);
                    listTime.add(myCalenderTime.getTimeInMillis());
                    Log.i(TAG, "onTimeSet: "+hourOfDay);
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                myTimeListener, hourOfDay, minute, false);
        Log.i(TAG, "showHourPicker: "+hourOfDay);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();


    }

    public void showStartDatePicker() {
        startCalender = Calendar.getInstance();
        int year = startCalender.get(Calendar.YEAR);
        int month = startCalender.get(Calendar.MONTH);
        int day = startCalender.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                if (view.isShown()) {
                    startCalender.set(Calendar.YEAR, year);
                    startCalender.set(Calendar.MONTH, month);
                    startCalender.set(Calendar.DAY_OF_MONTH, day);
                    month = month + 1;
                    startDate = day + "/" + month + "/" + year;
                    binding.startDateTxt.setText(startDate);
                }
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                myDateListener, year, month, day);
        datePickerDialog.setTitle("Choose start date:");

        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();
    }

    public void showEndDatePicker() {
        endCalender = Calendar.getInstance();
        int selectedYear = endCalender.get(Calendar.YEAR);
        int selectedMonth = endCalender.get(Calendar.MONTH);
        int selectedDay = endCalender.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener myDateListener = (view, year, month, day) -> {
            if (view.isShown()) {
                endCalender.set(Calendar.YEAR, year);
                endCalender.set(Calendar.MONTH, month);
                endCalender.set(Calendar.DAY_OF_MONTH, day);
                month = month + 1;
                endDate = day + "/" + month + "/" + year;
                binding.endDateTxt.setText(endDate);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                myDateListener, selectedYear, selectedMonth, selectedDay);
        datePickerDialog.setTitle("Choose start date:");

        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();
    }


    private Medicine createMedicine() {
        medicine = new Medicine();
        medicine.setName(binding.medName.getEditableText().toString());
        medicine.setStrength(binding.strength.getSelectedItem().toString());
        medicine.setIconType(s);
        medicine.setStartDate(startCalender.getTimeInMillis());
        medicine.setEndDate(endCalender.getTimeInMillis());
        medicine.setDuration(binding.durationMenu.getSelectedItem().toString());
        medicine.setNumOfPills(Integer.parseInt(binding.txtAmount.getText().toString()));
        medicine.setFrequencyPerDay(Integer.parseInt(binding.txtFrequence.getText().toString()));
        medicine.setNoOfStrength(Integer.parseInt(binding.noOfStrength.getEditableText().toString()));
        medicine.setTimes(listTime);
        medicine.setIsRefillReminder(binding.refillSwitch.isChecked());
        medicine.setInstructions(binding.instructionMenu.getSelectedItem().toString());
        medicine.setActive(true);
        binding.saveBtn.setText("SAVE");

        return medicine;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenterInterface=new AddMedicinePresenter(Repository.getInstance(ConcreteLocalSource.getInstance(getContext()),getContext())
                ,this);
        navController = Navigation.findNavController(view);
        binding.calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStartDatePicker();
            }
        });
        binding.calenderEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEndDatePicker();

            }
        });
        binding.timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHourPicker();
            }
        });

        binding.pillBtn.setOnClickListener(this);
        binding.bottleBtn.setOnClickListener(this);
        binding.dropBtn.setOnClickListener(this);
        binding.injectionBtn.setOnClickListener(this);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());

        Medicine editMedicine;

        if (AddMedicineFragmentArgs.fromBundle(getArguments()).getMedicine().getName() != null) {
            editMedicine = AddMedicineFragmentArgs.fromBundle(getArguments()).getMedicine();
            binding.medName.setText(editMedicine.getName());
            binding.noOfStrength.setText(String.valueOf(editMedicine.getNoOfStrength()));
            String startDateString = DateFormat.format("MM/dd/yyyy", new Date(editMedicine.getStartDate())).toString();
            binding.startDateTxt.setText(startDateString);
            String endDateString = DateFormat.format("MM/dd/yyyy", new Date(editMedicine.getEndDate())).toString();
            binding.endDateTxt.setText(endDateString);
            binding.txtAmount.setText(String.valueOf(editMedicine.getNumOfPills()));
            binding.txtFrequence.setText(String.valueOf(editMedicine.getFrequencyPerDay()));
            for (int i = 0; i < editMedicine.getTimes().size(); i++) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" hh-mm a");
                String dateTime = simpleDateFormat.format(editMedicine.getTimes().get(i));
                binding.txtTime.append(dateTime);
            }
            binding.durationMenu.setSelection(((ArrayAdapter) binding.durationMenu.getAdapter())
                    .getPosition(editMedicine.getDuration()));
            binding.saveBtn.setText("UPDATE");
            binding.saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editMedicine.setName(binding.medName.getEditableText().toString());
                    editMedicine.setStrength(binding.strength.getSelectedItem().toString());
                    editMedicine.setIconType(s);
                    if (startDate == null) {
                        editMedicine.setStartDate(editMedicine.getStartDate());
                    } else {
                        editMedicine.setStartDate(startCalender.getTimeInMillis());
                    }
                    if (endDate == null) {
                        editMedicine.setEndDate(editMedicine.getStartDate());
                    } else {
                        editMedicine.setEndDate(endCalender.getTimeInMillis());
                    }
                    editMedicine.setDuration(binding.durationMenu.getSelectedItem().toString());
                    editMedicine.setNumOfPills(Integer.parseInt(binding.txtAmount.getText().toString()));
                    editMedicine.setFrequencyPerDay(Integer.parseInt(binding.txtFrequence.getText().toString()));
                    editMedicine.setNoOfStrength(Integer.parseInt(binding.noOfStrength.getEditableText().toString()));
                    editMedicine.setTimes(listTime);
                    editMedicine.setInstructions(binding.instructionMenu.getSelectedItem().toString());
                    editMedicine.setIsRefillReminder(binding.refillSwitch.isChecked( ));
                    editMedicine.setActive(true);
                    presenterInterface.editMedicine(editMedicine);


                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                    Query query = ref.child(FirebaseAuth.getInstance().getUid()).child("medicine").orderByChild("name").equalTo(editMedicine.getName());

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot db: dataSnapshot.getChildren()) {
                                db.getRef().setValue(editMedicine);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), "faild", Toast.LENGTH_SHORT).show();

                        }
                    });
                    navController.popBackStack();
                }
            });
        } else {
            binding.saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String userId = FirebaseAuth.getInstance().getUid();
                    if(userId==null){
                       presenterInterface.addMedicine(createMedicine());

                    }else{
                        presenterInterface.addMedicine(createMedicine());
                        Log.i(TAG, "onClick: "+createMedicine().getMed_id());
                        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        User user=snapshot.getValue(User.class);
                                        user.getMedicine().add(createMedicine());
                                        databaseReference.child(userId).setValue(user);

                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });

                    }

                 navController.popBackStack();
                }
            });
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setRefill()
    {
        if(medicine.getNumOfPills()<=2)
        {
            AddMedReminder addMedReminder=new AddMedReminder(getContext());
            addMedReminder.addMedReminder();
        }
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
    public void editMedicine(Medicine medicine) {
        presenterInterface.editMedicine(medicine);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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