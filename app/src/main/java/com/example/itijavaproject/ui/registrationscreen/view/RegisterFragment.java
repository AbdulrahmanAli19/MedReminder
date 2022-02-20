package com.example.itijavaproject.ui.registrationscreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentRegisterBinding;

import com.example.itijavaproject.pojo.model.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;


public class RegisterFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "RegisterFragment.DEV";
    public FragmentRegisterBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSignUp.setOnClickListener(this);

    }

    private User createUser() {
        User user = new User();
        user.setFullName(binding.edtName.getEditText().getEditableText().toString().toLowerCase(Locale.ROOT));
        user.setPhoneNumber(binding.edtPhoneNumber.getEditText().toString());
        user.setEmail(binding.edtEmail.getEditText().getEditableText().toString());
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
        } else if (binding.edtName.getEditText().getText().toString().isEmpty()) {
            binding.edtName.setError(getString(R.string.empty_name));

        } else if (binding.edtPhoneNumber.getEditText().getText().toString().isEmpty()) {
            binding.edtPhoneNumber.setError(getString(R.string.empty_phone));

        } else if (binding.edtBirthday.getEditText().getText().toString().isEmpty()) {
            binding.edtPhoneNumber.setError(getString(R.string.empty_birthday));

        } else if (binding.genderGroup.getCheckedRadioButtonId() == -1) {
            Snackbar.make(getContext(), getView(),
                    getContext().getString(R.string.empty_gender),
                    Snackbar.LENGTH_LONG)
                    .show();
        } else {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                Log.d(TAG, "onClick: Clicked.");
                if (isDataValid()) {
                    ///TODO: send data to firebase
                    User user = createUser();
                }
                break;
            default:
                break;
        }
    }
}