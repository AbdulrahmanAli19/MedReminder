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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import io.paperdb.Paper;


public class UserFragment extends Fragment implements UserAdapter.UserFragInterface {
    private static final String TAG = "UserFragment.dev";
    private FragmentUserBinding binding;
    private NavController navController;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    private UserAdapter userAdapter;
    private boolean isSigned;

    public UserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Paper.init(getContext());
        navController = Navigation.findNavController(view);
        binding.recTacker.setLayoutManager(new LinearLayoutManager(getContext()));
        if (auth.getCurrentUser() != null) {
            isSigned = true;
            binding.txtAccount.setText(auth.getCurrentUser().getEmail());
            binding.txtName.setText(auth.getCurrentUser().getDisplayName());
        } else {
            binding.txtAccount.setText("no-email");
            binding.txtName.setText("Gust");
            Log.d(TAG, "onViewCreated: called");
            binding.btnSingOut.setText("Sign-in");

        }
        binding.recTacker.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserAdapter(this);

        binding.btnSingOut.setOnClickListener(v -> {
            if (auth.getCurrentUser() != null)
                auth.signOut();
            navController.popBackStack();
        });
        addNewTacker();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void addNewTacker() {
        List<Request> requestList = new ArrayList<>();
        if (auth.getCurrentUser() != null) {
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
                        userAdapter.setRequest(requestList);
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

    @Override
    public void onItemClick(int pos, Request selectedReg) {
        Paper.book().write("selectedUser", selectedReg.getSenderUid());
        Log.d(TAG, "onItemClick: " + Paper.book().read("selectedUser"));
    }
}