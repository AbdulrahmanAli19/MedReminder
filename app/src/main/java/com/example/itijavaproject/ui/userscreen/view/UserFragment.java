package com.example.itijavaproject.ui.userscreen.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itijavaproject.databinding.FragmentUserBinding;
import com.example.itijavaproject.pojo.model.ListOfRequest;
import com.example.itijavaproject.pojo.model.Request;
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
    RecyclerView recyclerView;
    private Request request = new Request();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
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
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Query query = databaseReference.child(FirebaseAuth.getInstance().getUid()).child("recivedRequests")
              .orderByChild("state").equalTo(true);
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
                    Snackbar snack = Snackbar.make(getContext(), getView(), "not Tacker yet", Snackbar.LENGTH_LONG);
                    View view = snack.getView();
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    view.setLayoutParams(params);
                    snack.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}