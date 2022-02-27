package com.example.itijavaproject.ui.sigininscreen.view;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.itijavaproject.MainActivity;
import com.example.itijavaproject.R;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class SigningFragment extends Fragment {

    private static final String TAG = "SigninFragment.DEV";
    private NavController navController;
    final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(), this::onSignInResult);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        MainActivity m = (MainActivity) getActivity();
        navController = m.getNavController();

        List<AuthUI.IdpConfig> provider = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(provider)
                .setTheme(R.style.GreenTheme)
                .setLogo(R.drawable.med_ic)
                .setTosAndPrivacyPolicyUrls("https://google.com", "https://githup.com")
                .build();
        launcher.launch(intent);
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        if (result.getResultCode() == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                if (user.getMetadata() != null) {
                    if (user.getMetadata().getCreationTimestamp() != user.getMetadata().getLastSignInTimestamp()) {
                        Toast.makeText(getContext(), "Welcome Back " + user.getDisplayName(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                    }
                    navController.popBackStack();
                }
            }
        } else {
            IdpResponse response = result.getIdpResponse();
            if (response == null) {
                Toast.makeText(getContext(), "Canceled.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Unknown Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onCreate: ActivityResult" + response.getError());
            }
            navController.popBackStack();
        }
    }
}