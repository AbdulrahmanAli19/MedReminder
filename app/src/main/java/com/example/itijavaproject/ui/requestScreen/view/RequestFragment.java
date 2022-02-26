package com.example.itijavaproject.ui.requestScreen.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.itijavaproject.databinding.FragmentRequestBinding;
import com.example.itijavaproject.pojo.model.ListOfRequest;
import com.example.itijavaproject.pojo.model.Request;
import com.example.itijavaproject.pojo.model.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class RequestFragment extends Fragment {
    private static final String TAG = "RequestFragment";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance()
    .getUid()).child("recivedRequests");
    private FragmentRequestBinding binding;
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
       ListOfRequest list = new ListOfRequest();
       List<Request> requestList=new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Log.d(TAG, "onDataChange snap: "+snapshot.exists());
//                    for (Request request:user.getRequestList()) {
//                        Log.d(TAG, "onDataChange request: "+user.getRequestList().isEmpty());
//                        if(request.getReceiverMail().toLowerCase(Locale.ROOT).equals(FirebaseAuth.getInstance()
//                        .getCurrentUser().getEmail().toLowerCase(Locale.ROOT)))
//                        {
//                            Log.d(TAG, "onDataChange user: "+user.getRequestList().isEmpty());
//                            list.getRequestList().add(request);
//                        }
//                    }
                    for (DataSnapshot snapshot1:snapshot.getChildren()) {
                      Request request = snapshot1.getValue(Request.class);
                        requestList.add(request);
                    }
                    Log.d(TAG, "onDataChange adapter: " + list.getRequestList().isEmpty());
                    requestAdapter = new RequestAdapter(requestList, getContext());
                    binding.recRequest.setAdapter(requestAdapter);
                }
                else {
                    Snackbar.make(getContext(), getView(), "not requests yet", Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(getContext(), getView(), "error", Snackbar.LENGTH_LONG).show();
            }
        });

    }
}