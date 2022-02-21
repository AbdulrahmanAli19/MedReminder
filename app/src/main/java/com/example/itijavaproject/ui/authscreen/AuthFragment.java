package com.example.itijavaproject.ui.authscreen;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentAuthBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AuthFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "AuthFragment.DEV";
    private FragmentAuthBinding binding;
    private NavController navController;
    private DatabaseReference ref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.btnSkip.setOnClickListener(this);
        binding.fabSinIn.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        FirebaseAuth aut = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("users");
        if (aut.getCurrentUser() != null)
            ref.child(aut.getCurrentUser().getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d(TAG, "onDataChange: ");
                            if (snapshot.exists()) {
                                navController.navigate(AuthFragmentDirections.actionAuthFragment2ToHomeFragment());
                            } else {
                                navController.navigate(AuthFragmentDirections.actionAuthFragmentToRegisterFragment());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(TAG, "onCancelled: ");
                        }
                    });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSkip:
                new AuthDialogFrag(navController)
                        .show(getActivity().getSupportFragmentManager(), "ALERT");
                break;
            case R.id.fabSinIn:
                navController.navigate(AuthFragmentDirections.actionAuthFragment2ToSigninFragment());
                break;
            default:
                Log.d(TAG, "onClick: unkowen id " + v.getId());
        }
    }
}