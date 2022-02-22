package com.example.itijavaproject.ui.healthTakerScreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itijavaproject.databinding.FragmentAddHealthTakerBinding;
import com.example.itijavaproject.pojo.model.Request;
import com.example.itijavaproject.pojo.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddHealthTakerFragment extends Fragment {
    private FragmentAddHealthTakerBinding binding;
    private static final String TAG = "AddHealthTakerFragment";
    DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        binding.btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (isValid()) {
                    auth.fetchSignInMethodsForEmail(binding.txtEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    boolean dol = task.getResult().getSignInMethods().isEmpty();
                                    if (!dol) {
                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Log.d(TAG, "onDataChange: no data");
                                                for (DataSnapshot mySnap : snapshot.getChildren()) {
                                                    User user = mySnap.getValue(User.class);
                                                    if (user.getEmail().toLowerCase(Locale.ROOT)
                                                            .equals(binding.txtEmail.getText().toString()
                                                                    .toLowerCase(Locale.ROOT)))
                                                    {
                                                        List<Request>requestList=new ArrayList<>();
                                                        Request request = new Request();
                                                        request.setId_patient(auth.getCurrentUser().getUid());
                                                        request.setMail_tacker(binding.txtEmail.getText().toString());
                                                        requestList.add(request);
                                                        user.setRequestList(requestList);
                                                        sendRequest(user);
                                                    }
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.d(TAG, "onCancelled: ");
                                            }
                                        });
                                    } else {
                                        Snackbar.make(getContext(), getView(), "user Not Found", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

        });
    }
    private void sendRequest(User user){
        databaseReference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "onSuccess: ");
            }
        });
    }
//    private boolean isValid(){
//        if (binding.txtEmail.getText().toString().isEmpty()) {
//            binding.txtEmail.setError(getString(R.string.empty_email));
//            String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
//            if (!binding.txtEmail.getText().toString().equals(emailRegex)) {
//                binding.txtEmail.setError(getString(R.string.not_vaild_email));
//            }
//            return false;
//        }
//        return false;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddHealthTakerBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
}