package com.example.itijavaproject.ui.userscreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentMedicationDisplayBinding;
import com.example.itijavaproject.databinding.FragmentRequestBinding;
import com.example.itijavaproject.databinding.FragmentUserBinding;
import com.example.itijavaproject.pojo.model.ListOfRequest;
import com.example.itijavaproject.pojo.model.Request;
import com.example.itijavaproject.ui.requestScreen.view.RequestAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {
    private static final String TAG = "UserFragment";
    private FragmentUserBinding binding;
    NavController navController;
    NavDirections directions;
    RecyclerView recyclerView;
    private Request request = new Request();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    //            .child(FirebaseAuth.getInstance()
//            .getUid()).child("recivedRequests");
    UserAdapter userAdapter;


    public UserFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recTacker.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.txtAccount.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        binding.recTacker.setLayoutManager(new LinearLayoutManager(getContext()));
        addNewTacker();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void addNewTacker() {
        ListOfRequest list = new ListOfRequest();
        List<Request> requestList = new ArrayList<>();
      databaseReference.child(FirebaseAuth.getInstance().getUid());
        Query query = databaseReference.child(FirebaseAuth.getInstance().getUid()).child("recivedRequests")
                .orderByChild("state").equalTo("true");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d(TAG, "onDataChange snap: " + snapshot.exists());
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Request request = snapshot1.getValue(Request.class);
                        requestList.add(request);
                    }
                    Log.d(TAG, "onDataChange adapter: " + list.getRequestList().isEmpty());
                    userAdapter = new UserAdapter(requestList, getContext());
                    binding.recTacker.setAdapter(userAdapter);
                } else {
                    Snackbar.make(getContext(), getView(), "not Tacker yet", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//                .child("recivedRequests");
//        query.child(request.getSenderMail().split("\\.")[0]).child("state").orderByChild("true");
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists())
//                {
//                    Log.d(TAG, "onDataChange snap: "+snapshot.exists());
//                    for (DataSnapshot snapshot1:snapshot.getChildren()) {
//                        Request request = snapshot1.getValue(Request.class);
//                        requestList.add(request);
//                    }
//                    Log.d(TAG, "onDataChange adapter: " + list.getRequestList().isEmpty());
//                    userAdapter = new UserAdapter(requestList, getContext());
//                    binding.recTacker.setAdapter(userAdapter);
//                }
//                else {
//                    Snackbar.make(getContext(), getView(), "not requests yet", Snackbar.LENGTH_LONG).show();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Snackbar.make(getContext(), getView(), "error", Snackbar.LENGTH_LONG).show();
//            }
//        });

    }
}