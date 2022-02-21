package com.example.itijavaproject.ui.homescreen.presenter;

import android.os.Handler;

import com.example.itijavaproject.pojo.repo.RepositoryInterface;
import com.example.itijavaproject.ui.homescreen.view.HomeFragInterface;

public class HomePresenter implements HomePresenterInterface {

    private final RepositoryInterface repo;
    private final HomeFragInterface home;

    public HomePresenter(HomeFragInterface home, RepositoryInterface repository) {
        this.home = home;
        this.repo = repository;
    }

    @Override
    public void getSelectedDateMedicines(Long date) {
        home.getSelectedDateMedicines(repo.getSelectedDateMedicines(date));
    }
}
