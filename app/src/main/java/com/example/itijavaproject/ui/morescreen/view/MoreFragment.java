package com.example.itijavaproject.ui.morescreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentMoreBinding;
import com.example.itijavaproject.ui.medicationsscreen.view.MedicationsFragmentDirections;


public class MoreFragment extends Fragment {
    Button btnRequest;
     FragmentMoreBinding binding;
     NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        btnRequest=view.findViewById(R.id.btnRequest);
        navController = Navigation.findNavController(view);
    }
}