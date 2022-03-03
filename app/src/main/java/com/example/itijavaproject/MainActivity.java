package com.example.itijavaproject;


import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.itijavaproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements
        NavController.OnDestinationChangedListener {

    private static final String TAG = "MainActivity.DEV";
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolBar);
        setUpNavigation();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void setUpNavigation() {
        navController = Navigation.findNavController(this, R.id.fragmentContainerView);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment,
                R.id.userFragment, R.id.requestFragment, R.id.signinFragment, R.id.splashFragment,
                R.id.medicationsFragment, R.id.registerFragment).build();


        setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.toolBar,
                navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
        navController.addOnDestinationChangedListener(this);
    }

    public NavController getNavController() {
        return navController;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onDestinationChanged(@NonNull NavController navController,
                                     @NonNull NavDestination navDestination,
                                     @Nullable Bundle bundle) {

        switch (navDestination.getId()) {
            case R.id.medicationDisplayFragment:
            case R.id.addMedicineFragment:
            case R.id.addHealthTakerFragment:
                binding.toolBar.setVisibility(View.VISIBLE);
                binding.appBarLayout.setVisibility(View.VISIBLE);
                binding.bottomNavigation.setVisibility(View.GONE);
                break;
            case R.id.splashFragment:
            case R.id.signinFragment:
                binding.toolBar.setVisibility(View.VISIBLE);
                binding.bottomNavigation.setVisibility(View.GONE);
                break;
            case R.id.homeFragment:
                binding.toolBar.setVisibility(View.GONE);
                binding.appBarLayout.setVisibility(View.GONE);
                binding.bottomNavigation.setVisibility(View.VISIBLE);
                binding.appBarLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.medicationsFragment:
            case R.id.userFragment:
            case R.id.recRequest:
            case R.id.requestFragment:
                binding.toolBar.setVisibility(View.VISIBLE);
                binding.bottomNavigation.setVisibility(View.VISIBLE);
                binding.appBarLayout.setVisibility(View.VISIBLE);
                break;
            default:
                Log.d(TAG, "onDestinationChanged: unknown destiny " + navDestination.getDisplayName());
                break;
        }
    }
}