package com.example.itijavaproject.ui.medicationDisplay.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.itijavaproject.R;
import com.example.itijavaproject.data.db.ConcreteLocalSource;
import com.example.itijavaproject.data.db.DatabaseAccess;
import com.example.itijavaproject.databinding.FragmentMedicationDisplayBinding;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.Repository;
import com.example.itijavaproject.ui.medicationDisplay.presenter.MedicationDisplayPresenter;
import com.example.itijavaproject.ui.medicationDisplay.presenter.MedicineDisplayPresenterInterface;
import com.example.itijavaproject.workers.AddRefillReminder;
import com.example.itijavaproject.workers.MedReminderUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.threeten.bp.LocalDate;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MedicationDisplayFragment extends Fragment implements MedicineDisplayInterface {
    private static final String TAG = "MedicationDisplayFragme";
    private FragmentMedicationDisplayBinding binding;
    private MedicineDisplayPresenterInterface presenter;
    private Medicine selectedMed = new Medicine();
    NavController navController;
    NavDirections directions;
    DatabaseAccess databaseAccess;
    Medicine medicine;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("medicine");

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


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new MedicationDisplayPresenter(Repository.getInstance(ConcreteLocalSource.getInstance(getContext()), getContext())
                , this);
        databaseAccess = DatabaseAccess.getInstance(getActivity().getApplicationContext());
        navController = Navigation.findNavController(view);
        medicine = MedicationDisplayFragmentArgs.fromBundle(getArguments()).getObjOfMeds();
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
        Date startDate = new Date(medicine.getStartDate());
        Date endDate = new Date(medicine.getEndDate());
        long duration = endDate.getTime() - startDate.getTime();
        LocalDate localDate = LocalDate.ofEpochDay(duration);
        binding.txtGetDuration.setText(medicine.getDuration() + " ,take for\n" + localDate.getDayOfMonth()
                + " day(s)\n"+localDate.getMonthValue()+"month(s)");
        binding.noPills.append("No.of Pills: " + medicine.getNumOfPills());
        for (int i = 0; i < medicine.getTimes().size(); i++) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" hh:mm a");
            String dateTime = simpleDateFormat.format(medicine.getTimes().get(i));
            binding.txtTimes.append(dateTime + " take 1 pill(s) \n");
        }

        if (medicine.isActive()) {
            binding.suspendBtn.setText("SUSPEND");
            binding.suspendBtn.setOnClickListener(view1 -> {
                        binding.suspendBtn.setText("ACTIVE");
                        medicine.setActive(false);
                        MedReminderUtil.removeMedReminder(medicine.getMed_id(), getContext());
                        AddRefillReminder.removeRefillReminder(medicine.getMed_id(), getContext());
                        new Thread(() -> databaseAccess.medicineDao().updateMedicine(medicine)).start();
                    }
            );

        } else {
            binding.suspendBtn.setText("ACTIVE");
            binding.suspendBtn.setOnClickListener(view12 -> {
                for (long time : medicine.getTimes()) {
                    MedReminderUtil.addMedReminder(time, getContext(), medicine.getMed_id());
                }
                AddRefillReminder.addRefill(getContext(), medicine.getMed_id());
                binding.suspendBtn.setText("SUSPEND");
                medicine.setActive(true);
                new Thread(() -> databaseAccess.medicineDao().updateMedicine(medicine)).start();
            });
        }
        binding.refillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });
        binding.addDoseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                directions = MedicationDisplayFragmentDirections.actionMedicationDisplayFragmentToAddMedicineFragment2(medicine);
                navController.navigate(directions);
            }
        });
        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.deleteMedicine(medicine);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                Query query = ref.child(FirebaseAuth.getInstance().getUid()).child("medicine").orderByChild("med_id").equalTo(medicine.getMed_id());

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot db : dataSnapshot.getChildren()) {
                            db.getRef().removeValue();
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

        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                directions = MedicationDisplayFragmentDirections.actionMedicationDisplayFragmentToAddMedicineFragment2(medicine);
                navController.navigate(directions);
            }
        });
    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        presenter.deleteMedicine(medicine);

    }

    @Override
    public void editMedicine(Medicine medicine) {
        presenter.editMedicine(medicine);

    }

    public void createDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogLayout = inflater.inflate(R.layout.custom_row_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogLayout);
        TextView Refill = dialogLayout.findViewById(R.id.txtmsgRefill);
        TextView addText = dialogLayout.findViewById(R.id.txtadd);
        TextView numpill = dialogLayout.findViewById(R.id.numPills);
        TextView txtmsg = dialogLayout.findViewById(R.id.txtmsg);
        final EditText AddEdit = (EditText) dialogLayout.findViewById(R.id.editRefill);
        numpill.append("you have " + medicine.getNumOfPills());
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (AddEdit.getText().toString().isEmpty()) {
                    Snackbar.make(getContext(), getView(), "please enter number of pills", Snackbar.LENGTH_LONG).show();
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
}