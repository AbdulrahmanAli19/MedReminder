package com.example.itijavaproject.ui.requestScreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentMedicationsBinding;
import com.example.itijavaproject.databinding.FragmentRegisterBinding;
import com.example.itijavaproject.databinding.FragmentRequestBinding;
import com.example.itijavaproject.pojo.model.ListOfRequest;
import com.example.itijavaproject.pojo.model.Request;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


public class RequestFragment extends Fragment {
    private static final String TAG = "RequestFragment";
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Requests");
    private FragmentRequestBinding binding;
    NavController navController;
   RecyclerView.LayoutManager layoutManager;
   RequestAdapter requestAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRequestBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recRequest.setLayoutManager(new LinearLayoutManager(getContext()));
        addNewRequest();

    }
    private void addNewRequest() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListOfRequest listOfRequest = snapshot.getValue(ListOfRequest.class);
                ListOfRequest list=new ListOfRequest();
                Log.d(TAG, "onDataChange: "+listOfRequest.getRequestList().size());
                for ( Request request:listOfRequest.getRequestList()) {
                    if (request.getReceiverMail().toLowerCase(Locale.ROOT).equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toLowerCase(Locale.ROOT)))
                    {
                        list.getRequestList().add(request);
                    }
                }
                Log.d(TAG, "onDataChange: "+list.getRequestList().isEmpty());
                 requestAdapter=new RequestAdapter(list,getContext());
                binding.recRequest.setAdapter(requestAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(getContext(), getView(), "error", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}