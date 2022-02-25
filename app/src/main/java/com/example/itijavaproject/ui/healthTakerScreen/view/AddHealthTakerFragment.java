package com.example.itijavaproject.ui.healthTakerScreen.view;

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
import com.example.itijavaproject.databinding.FragmentAddHealthTakerBinding;
import com.example.itijavaproject.pojo.model.ListOfRequest;
import com.example.itijavaproject.pojo.model.Request;
import com.example.itijavaproject.pojo.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddHealthTakerFragment extends Fragment {
    private FragmentAddHealthTakerBinding binding;
    private static final String TAG = "AddHealthTakerFragment";
    DatabaseReference databaseReference;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    NavController navController;
    String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth.fetchSignInMethodsForEmail(binding.txtEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                boolean dol = task.getResult().getSignInMethods().isEmpty();
                                if (dol) {
                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Log.d(TAG, "onDataChange: no data");
                                            ListOfRequest listOfRequest = new ListOfRequest();
                                            Request request = new Request();
                                            request.setReceiverMail(binding.txtEmail.getText().toString());
                                            request.setSenderMail(auth.getCurrentUser().getEmail());
//                                             request.setSenderUid(FirebaseAuth.getInstance().getUid());
                                            request.setShared(binding.boxPolicy.isChecked());
                                            listOfRequest.getRequestList().add(request);
                                            checkRequests(listOfRequest);
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

    private void checkRequests(ListOfRequest request) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    addRequestSendeer(request);
                    addRequestReciver(request);
                } else {
                    databaseReference.setValue(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Snackbar.make(getContext(), getView(), "Success", Snackbar.LENGTH_LONG).show();
                            navController.popBackStack();

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(getContext(), getView(), "error", Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void addRequestSendeer(ListOfRequest request) {
        userId = FirebaseAuth.getInstance().getUid();
        databaseReference.child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        user.getRequestList().add(request.getRequestList().get(0));

                        Map<String, Object> map = new HashMap<>();
                        map.put("senderRequests", request);
                        databaseReference.child(userId).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Snackbar.make(getContext(), getView(), "Success", Snackbar.LENGTH_LONG).show();
                                navController.popBackStack();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(getContext(), getView(), "error" + e.getMessage(), Snackbar.LENGTH_LONG).show();

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Snackbar.make(getContext(), getView(), "error", Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    public void addRequestReciver(ListOfRequest listOfRequest) {
        String email = listOfRequest.getRequestList().get(0).getReceiverMail();
        userId = FirebaseAuth.getInstance().getUid();
        Query query = databaseReference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> map = new HashMap<>();
                map.put("recivedRequests", listOfRequest);
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    snapshot1.getRef().updateChildren(map);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private boolean isVaild() {
        if (binding.txtEmail.getText().toString().isEmpty()) {
            binding.txtEmail.setError(getString(R.string.empty_email));
            String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
            if (!binding.txtEmail.getText().toString().equals(emailRegex)) {
                binding.txtEmail.setError(getString(R.string.not_vaild_email));
            }
            return false;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddHealthTakerBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

}