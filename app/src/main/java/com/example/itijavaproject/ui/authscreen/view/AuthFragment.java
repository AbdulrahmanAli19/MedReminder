package com.example.itijavaproject.ui.authscreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentAuthBinding;
import com.firebase.ui.auth.AuthUI;

import java.util.List;


public class AuthFragment extends Fragment {

    private NavController navController;
    private List<AuthUI.IdpConfig> configs;
    private FragmentAuthBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.btnPhoneNumber.setOnClickListener(v -> navController.navigate(R.id.action_authFragment_to_registerFragment));
        binding.btnSkip.setOnClickListener(v -> {
            AuthDialogFrag dialog = new AuthDialogFrag(navController);
            dialog.show(getActivity().getSupportFragmentManager(), "test");
        });
    }
}