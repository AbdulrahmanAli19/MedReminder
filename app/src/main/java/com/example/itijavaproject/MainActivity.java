package com.example.itijavaproject;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpNavigation();

    }

    private void setUpNavigation(){
        navController = Navigation.findNavController(this, R.id.fragmentContainerView);

        appBarConfiguration = new AppBarConfiguration
                .Builder(navController.getGraph())
                .build();

        setupActionBarWithNavController(this, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}