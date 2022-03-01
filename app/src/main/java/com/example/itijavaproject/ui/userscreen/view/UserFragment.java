package com.example.itijavaproject.ui.userscreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentMedicationDisplayBinding;
import com.example.itijavaproject.databinding.FragmentUserBinding;
import com.example.itijavaproject.pojo.model.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;


public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    NavController navController;
    NavDirections directions;
    RecyclerView recyclerView;
    private Request request=new Request();
    private DatabaseReference databaseReference;


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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}