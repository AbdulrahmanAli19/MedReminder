package com.example.itijavaproject;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.itijavaproject.databinding.ActivityMainBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.collapsingLayout.setExpandedTitleColor(getResources().getColor(R.color.transperent));
        binding.collapsingLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolBar);
        setUpNavigation();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void setUpNavigation() {
        navController = Navigation.findNavController(this, R.id.fragmentContainerView);

        appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.authFragment, R.id.homeFragment, R.id.splashFragment)
                .build();

        binding.calendarView.setSelectedDate(CalendarDay.today());
        NavigationUI.setupWithNavController(binding.collapsingLayout, binding.toolBar,
                navController, appBarConfiguration);
    }



    public void showNavBar() {
        binding.calendarView.setVisibility(View.VISIBLE);
    }

    public void hideNavBar() {
        binding.calendarView.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}