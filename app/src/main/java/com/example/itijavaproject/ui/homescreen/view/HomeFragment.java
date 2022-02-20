package com.example.itijavaproject.ui.homescreen.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.itijavaproject.MainActivity;
import com.example.itijavaproject.R;
import com.example.itijavaproject.data.db.ConcreteLocalSource;
import com.example.itijavaproject.databinding.FragmentHomeBinding;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.Repository;
import com.example.itijavaproject.ui.homescreen.presenter.HomePresenter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class HomeFragment extends Fragment implements View.OnClickListener,
        HomeCommunicator, HomeFragInterface {
    private static final String TAG = "HomeFragment.DEV";
    private NavController navController;
    private FragmentHomeBinding binding;
    private HomePresenter presenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.fabAddHealthTacker.setOnClickListener(this);
        binding.fabAddMed.setOnClickListener(this);
        presenter = new HomePresenter(this, Repository.getInstance(ConcreteLocalSource
                .getInstance(this.getContext()), getContext()));
        ((MainActivity) getActivity()).setHomeCommunicator(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        NavDirections directions;
        switch (v.getId()) {
            case R.id.fabAddHealthTacker:
                directions = HomeFragmentDirections.actionHomeFragmentToAddHealthTakerFragment();
                navController.navigate(directions);
                break;
            case R.id.fabAddMed:
                directions = HomeFragmentDirections.actionHomeFragmentToAddMedicineFragment();
                navController.navigate(directions);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDateChange(@NonNull MaterialCalendarView widget,
                             @NonNull CalendarDay date, boolean selected) {
        presenter.getSelectedDateMedicines(date.getDate().toEpochDay());
    }

    @Override
    public void getSelectedDateMedicines(Maybe<List<Medicine>> medicines) {
        medicines.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<List<Medicine>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onSuccess(List<Medicine> medicines) {
                        for (Medicine med : medicines) {
                            Log.d(TAG, "onSuccess: " + med);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

}