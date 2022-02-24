package com.example.itijavaproject.ui.homescreen.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.itijavaproject.MainActivity;
import com.example.itijavaproject.R;
import com.example.itijavaproject.data.db.ConcreteLocalSource;
import com.example.itijavaproject.databinding.FragmentHomeBinding;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.Repository;
import com.example.itijavaproject.ui.homescreen.presenter.HomePresenter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class HomeFragment extends Fragment implements View.OnClickListener,
        HomeFragInterface, CurrentDayAdapter.HomeAdapterInterface,
        OnDateSelectedListener, HomeCommunicator{

    private static final String TAG = "HomeFragment.DEV";
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1230;
    private NavController navController;
    private FragmentHomeBinding binding;
    private HomePresenter presenter;
    private CurrentDayAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        binding.collapsingLayout.setExpandedTitleColor(getResources().getColor(R.color.transperent));
        binding.collapsingLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        binding.calendarView.setSelectedDate(CalendarDay.today());
        binding.calendarView.setOnDateChangedListener(this);
        binding.collapsingLayout.setTitle("Home");
        binding.fabAddHealthTacker.setOnClickListener(this);
        binding.fabAddMed.setOnClickListener(this);
        presenter = new HomePresenter(this, Repository
                .getInstance(ConcreteLocalSource.getInstance(this.getContext()), getContext()));
        checkPermission();
        adapter = new CurrentDayAdapter(getContext(), this);
        binding.homeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.homeRecycler.setAdapter(adapter);
        presenter.getSelectedDateMedicines(System.currentTimeMillis());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        NavDirections directions;
        switch (v.getId()) {
            case R.id.fabAddHealthTacker:
                directions = HomeFragmentDirections.actionHomeFragmentToAddHealthTakerFragment();
                navController.navigate(directions);
                break;
            case R.id.fabAddMed:
                directions = HomeFragmentDirections.actionHomeFragmentToAddMedicineFragment(new Medicine());
                navController.navigate(directions);
                break;
            default:
                break;
        }
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
                        Log.d(TAG, "onSuccess: " + medicines.isEmpty());
                        adapter.setMedicines(medicines);
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

    @Override
    public void onClickItemClickListener(int pos) {
        Log.d(TAG, "onClickItemClickListener: " + pos);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        LocalDate localDate = LocalDate.ofEpochDay(date.getDate().toEpochDay());
        long mili = localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        presenter.getSelectedDateMedicines(mili);
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getActivity())) {
            new HomePermissionDialog(this)
                    .show(getActivity().getSupportFragmentManager(), "OverAppsPermission");
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(getActivity())) {
                Log.d(TAG, "onActivityResult: permission denied");
            } else {
                Log.d(TAG, "onActivityResult: permission granted");
            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void getPermission() {
        if (!Settings.canDrawOverlays(getActivity())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getContext().getPackageName()));
            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
        }
    }
}