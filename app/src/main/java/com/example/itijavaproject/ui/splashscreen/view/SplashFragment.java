package com.example.itijavaproject.ui.splashscreen.view;

import static java.lang.Thread.sleep;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.itijavaproject.databinding.FragmentSplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SplashFragment extends Fragment {
    private static final String TAG = "SplashFragment";
    private NavController navController;
    private FragmentSplashBinding binding;
    private DatabaseReference dbRef;
    private FirebaseAuth auth;
    private String lastLifeCaller = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbRef = FirebaseDatabase.getInstance().getReference("users");
        auth = auth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        lastLifeCaller = "onViewCreated";
        new Thread(() -> {
            try {
                sleep(2000);
                handler.sendMessage(new Message());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onStart() {
        super.onStart();
        lastLifeCaller = "onStart";
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lastLifeCaller.equals("onPause") || lastLifeCaller.equals("onStop"))
            checkIfUserAuthenticated();
    }

    @Override
    public void onPause() {
        super.onPause();
        lastLifeCaller = "onPause";
    }

    @Override
    public void onStop() {
        super.onStop();
        lastLifeCaller = "onStop";
    }

    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            checkIfUserAuthenticated();
        }
    };

    private void checkIfUserAuthenticated() {
        if (auth.getCurrentUser() == null) {
            Log.d(TAG, "checkIfUserAuthenticated: null");
            new AuthDialogFrag(navController).show(getActivity().getSupportFragmentManager(), "SIGIN");
        } else {
            dbRef.child(auth.getCurrentUser().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                Log.d(TAG, "onDataChange: ");
                                navController.navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment());
                            } else {
                                navController.navigate(SplashFragmentDirections.actionSplashFragmentToRegisterFragment());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d(TAG, "onCancelled: "+error.getMessage());

                        }
                    });
        }
    }

}