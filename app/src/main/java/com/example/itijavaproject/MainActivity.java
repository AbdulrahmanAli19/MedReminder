package com.example.itijavaproject;


import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

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
import com.example.itijavaproject.ui.homescreen.view.HomeCommunicator;
import com.google.android.material.appbar.AppBarLayout;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

public class MainActivity extends AppCompatActivity implements
        NavController.OnDestinationChangedListener,
        AppBarLayout.OnOffsetChangedListener,
        OnDateSelectedListener {

    private static final String TAG = "MainActivity.DEV";
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private ActivityMainBinding binding;
    private HomeCommunicator homeCommunicator;

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
        binding.collapsingLayout.setExpandedTitleColor(getResources().getColor(R.color.transperent));
        binding.collapsingLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));

        navController = Navigation.findNavController(this, R.id.fragmentContainerView);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.homeFragment,
                R.id.moreFragment, R.id.signinFragment, R.id.splashFragment,
                R.id.medicationsFragment, R.id.authFragment).build();

        binding.calendarView.setSelectedDate(CalendarDay.today());

        setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.collapsingLayout, binding.toolBar,
                navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
        navController.addOnDestinationChangedListener(this);
        binding.calendarView.setOnDateChangedListener(this);
    }

    public NavController getNavController(){
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

    @Override
    public void onDestinationChanged(@NonNull NavController navController,
                                     @NonNull NavDestination navDestination,
                                     @Nullable Bundle bundle) {

        switch (navDestination.getId()) {
            case R.id.medicationDisplayFragment:
            case R.id.addMedicineFragment:
            case R.id.addHealthTakerFragment:
                binding.appBarLayout.setExpanded(false);
                binding.appBarLayout.setVisibility(View.VISIBLE);
                binding.bottomNavigation.setVisibility(View.GONE);
                break;
            case R.id.splashFragment:
            case R.id.signinFragment:
                binding.calendarView.setVisibility(View.GONE);
                binding.bottomNavigation.setVisibility(View.GONE);
                break;
            case R.id.homeFragment:
                binding.calendarView.setVisibility(View.VISIBLE);
                binding.bottomNavigation.setVisibility(View.VISIBLE);
                binding.appBarLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.medicationsFragment:
                binding.calendarView.setVisibility(View.GONE);
                binding.bottomNavigation.setVisibility(View.VISIBLE);
                binding.appBarLayout.setVisibility(View.VISIBLE);
                break;
            default:
                Log.d(TAG, "onDestinationChanged: unknown destiny " + navDestination.getDisplayName());
                break;
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == -binding.collapsingLayout.getHeight() + binding.toolBar.getHeight()) {
            Log.d(TAG, "onOffsetChanged: collapsed");
        } else {
            Log.d(TAG, "onOffsetChanged: opened");
        }
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget,
                               @NonNull CalendarDay date, boolean selected) {
        if (homeCommunicator != null)
            homeCommunicator.onDateChange(widget, date, selected);
    }

    public void setHomeCommunicator(HomeCommunicator homeCommunicator) {
        this.homeCommunicator = homeCommunicator;
    }

}