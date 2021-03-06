package com.example.itijavaproject.ui.healthTakerScreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
    private DatabaseReference databaseReference;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private NavController navController;
    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.btnInvite.setOnClickListener(view12 -> {
            if (isVaild()) {
                auth.fetchSignInMethodsForEmail(binding.txtEmail.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                boolean check = task.getResult().getSignInMethods().isEmpty();
                                if (!check) {
                                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            ListOfRequest listOfRequest = new ListOfRequest();
                                            Request request = new Request();
                                            request.setReceiverMail(binding.txtEmail.getText().toString());
                                            request.setSenderMail(auth.getCurrentUser().getEmail().toLowerCase(Locale.ROOT));
                                            request.setSenderUid(FirebaseAuth.getInstance().getUid());
                                            request.setShared(binding.boxPolicy.isChecked());
                                            listOfRequest.getRequestList().add(request);
                                            addRequestReciver(listOfRequest);
                                            Snackbar.make(getContext(), getView(), "Success", Snackbar.LENGTH_LONG).show();
                                            navController.popBackStack();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Log.d(TAG, "onCancelled: ");
                                        }
                                    });
                                } else {
                                    Snackbar snack = Snackbar.make(getContext(), getView(), "user not found", Snackbar.LENGTH_LONG);
                                    View view1 = snack.getView();
                                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view1.getLayoutParams();
                                    params.gravity = Gravity.TOP;
                                    view1.setLayoutParams(params);
                                    snack.show();
                                }
                            } else {
                                Toast.makeText(getContext(), "error in email", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    public void addRequestReciver(ListOfRequest listOfRequest) {
        String email = listOfRequest.getRequestList().get(0).getReceiverMail();
        userId = FirebaseAuth.getInstance().getUid();
        Query query = databaseReference.orderByChild("email").equalTo(email);
        Request request = listOfRequest.getRequestList().get(0);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    snapshot1.getRef().child("recivedRequests").child(request.getSenderMail().split("\\.")[0]).setValue(request);
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
        } else {
            return true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddHealthTakerBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

}