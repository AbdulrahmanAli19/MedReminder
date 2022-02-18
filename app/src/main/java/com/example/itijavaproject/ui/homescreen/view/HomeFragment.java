package com.example.itijavaproject.ui.homescreen.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentHomeBinding;
import com.example.itijavaproject.ui.medicationsscreen.view.MedicationsFragment;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private NavController navController;
    private FragmentHomeBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.fabAddHealthTacker.setOnClickListener(this);
        binding.fabAddMed.setOnClickListener(this);
        binding.fabMed.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        NavDirections directions;
        switch (v.getId()) {
            case R.id.fabAddHealthTacker:
                directions = HomeFragmentDirections.actionHomeFragmentToAddHealthTakerFragment();
                navController.navigate(directions);
                break;
            case R.id.fabAddMed:
                directions = HomeFragmentDirections.actionHomeFragmentToAddMedicineFragment();
                navController.navigate(directions);
                break;
            default:
                break;
            case R.id.fabMed:
                directions= HomeFragmentDirections.actionHomeFragmentToMedicationsFragment();
                navController.navigate(directions);
        }
    }
}