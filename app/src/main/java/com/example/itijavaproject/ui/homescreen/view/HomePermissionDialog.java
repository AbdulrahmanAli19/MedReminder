package com.example.itijavaproject.ui.homescreen.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class HomePermissionDialog extends DialogFragment {

    private final HomeCommunicator homeCommunicator;

    public HomePermissionDialog(HomeCommunicator homeCommunicator) {
        this.homeCommunicator = homeCommunicator;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Drawing over apps")
                .setMessage("Drawing over other applications permission is required!")
                .setPositiveButton("Grant", (dialog, which) -> {
                    homeCommunicator.getPermission();
                })
                .setNegativeButton("cancel", (dialog, which) -> {
                    Toast.makeText(getContext(), "dined", Toast.LENGTH_SHORT).show();
                });
        return builder.create();
    }

}
