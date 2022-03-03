package com.example.itijavaproject.ui.registrationscreen.view;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentRegisterBinding;

import com.example.itijavaproject.pojo.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class RegisterFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "RegisterFragment.DEV";
    private FragmentRegisterBinding binding;
    private NavController navController;
    private final Calendar myCalendar = Calendar.getInstance();
    private final String myFormat = "dd/MM/yyyy";
    private final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference("users");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.btnSignUp.setOnClickListener(this);
        binding.edtBirthday.setOnClickListener(this);

    }

    private User createUser() {
        User user = new User();
        user.setFullName(binding.edtName.getEditText().getEditableText().toString().toLowerCase(Locale.ROOT).trim());
        user.setPhoneNumber(binding.edtPhoneNumber.getEditText().getText().toString().trim());
        user.setEmail(binding.edtEmail.getEditText().getEditableText().toString().trim());
        user.setBirthday(myCalendar.getTimeInMillis());
        int id = binding.genderGroup.getCheckedRadioButtonId();
        user.setMale(id == binding.btnM.getId());
        return user;
    }

    private boolean isDataValid() {
        if (binding.edtEmail.getEditText().getText().toString().isEmpty()) {
            binding.edtEmail.setError(getString(R.string.empty_email));
            String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
            if (!binding.edtEmail.getEditText().getText().toString().equals(emailRegex)) {
                binding.edtEmail.setError(getString(R.string.not_vaild_email));
            }
            return false;
        }
        else if (binding.edtName.getEditText().getText().toString().isEmpty()) {
            binding.edtName.setError(getString(R.string.empty_name));

        }
        else if (binding.edtPhoneNumber.getEditText().getText().toString().isEmpty()) {
            binding.edtPhoneNumber.setError(getString(R.string.empty_phone));

        }
        else if (binding.edtBirthdayLayout.getEditText().getText().toString().isEmpty()) {
            binding.edtPhoneNumber.setError(getString(R.string.empty_birthday));

        }
        else if (binding.genderGroup.getCheckedRadioButtonId() == -1) {
            Snackbar.make(getContext(), getView(),
                    getContext().getString(R.string.empty_gender),
                    Snackbar.LENGTH_LONG)
                    .show();
        }
        else {
            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                Log.d(TAG, "onClick: Clicked.");
                if (isDataValid()) {
                    ///TODO: send data to firebase
                    User user = createUser();
                    String uid = FirebaseAuth.getInstance().getUid();
                    user.setUid(uid);
                    Log.d(TAG, "onClick: " + uid);
                    reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnSuccessListener(unused -> {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest
                                .Builder()
                                .setDisplayName(user.getFullName())
                                .build();
                        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
                        fUser.updateEmail(user.getEmail());
                        fUser.updateProfile(profileUpdates);
                        navController.navigate(RegisterFragmentDirections
                                .actionRegisterFragmentToHomeFragment());
                    }).addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Error " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    });
                }
                break;
            case R.id.edtBirthday:
                DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
                    myCalendar.set(YEAR, year);
                    myCalendar.set(MONTH, month);
                    myCalendar.set(DAY_OF_MONTH, dayOfMonth);
                    binding.edtBirthdayLayout.getEditText().setText(sdf.format(myCalendar.getTime()));
                };
                new DatePickerDialog(getContext(), date, myCalendar.get(YEAR), myCalendar.get(MONTH),
                        myCalendar.get(DAY_OF_MONTH)).show();
                Log.d(TAG, "onClick: " + v.getId());
                break;
            default:
                break;
        }
    }
}