package com.example.itijavaproject.ui.homescreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NavController navController;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        NavDirections directions = HomeFragmentDirections
                .actionHomeFragmentToMedicationsFragment("Hngeb A+");
        binding.btnTest.setOnClickListener(v -> navController
                .navigate(directions));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home,
                container, false);
        return binding.getRoot();
    }
}