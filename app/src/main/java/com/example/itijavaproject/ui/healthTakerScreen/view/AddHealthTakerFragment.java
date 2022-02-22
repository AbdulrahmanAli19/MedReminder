package com.example.itijavaproject.ui.healthTakerScreen.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.itijavaproject.R;

public class AddHealthTakerFragment extends Fragment {
Button btnInvite;
TextView txtEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    txtEmail= txtEmail.findViewById(R.id.txtEmail);
    btnInvite= btnInvite.findViewById(R.id.btnInvite);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_health_taker, container, false);
    }
}