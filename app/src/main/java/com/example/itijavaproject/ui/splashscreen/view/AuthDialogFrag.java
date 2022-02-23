package com.example.itijavaproject.ui.splashscreen.view;

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
        builder.setTitle("It's recommended to sign-in")
                .setMessage("You'll be get all of the app benefits by sign-in")
                .setPositiveButton("Sign-in", (dialog, which) -> {
                    navController.navigate(SplashFragmentDirections.actionSplashFragmentToSigninFragment());

                })
                .setNegativeButton("Continue", (dialog, which) -> {
                    navController.navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment());
                });
        return builder.create();
    }

}
