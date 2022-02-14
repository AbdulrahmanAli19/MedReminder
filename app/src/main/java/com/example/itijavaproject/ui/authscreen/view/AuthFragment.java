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
import com.firebase.ui.auth.AuthUI;

import java.util.List;


public class AuthFragment extends Fragment {

    private NavController navController;
    private List<AuthUI.IdpConfig> configs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        Button btnSkip = view.findViewById(R.id.btnSkip);
        Button btnGoogle = view.findViewById(R.id.btnGoogle);
        Button btnPhoneNum = view.findViewById(R.id.btnPhoneNumber);
        btnSkip.setOnClickListener(v -> {
            navController.navigate(R.id.action_authFragment_to_homeFragment);

        });
    }
}