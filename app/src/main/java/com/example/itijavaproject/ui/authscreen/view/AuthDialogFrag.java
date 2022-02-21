package com.example.itijavaproject.ui.authscreen.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;

public class AuthDialogFrag extends DialogFragment {

    private final NavController navController;

    public AuthDialogFrag(NavController navController) {
        this.navController = navController;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure you want to continue as a guest?")
                .setMessage("you won't be able to use all of the future's it's recommended to sigin in")
                .setPositiveButton("Sign-in", (dialog, which) -> {})
                .setNegativeButton("Continue", (dialog, which) -> {
                    navController.navigate(AuthFragmentDirections.actionAuthFragmentToHomeFragment());
                });
        return builder.create();
    }
}
