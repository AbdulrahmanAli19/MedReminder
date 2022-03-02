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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.itijavaproject.R;
import com.example.itijavaproject.data.db.ConcreteLocalSource;
import com.example.itijavaproject.databinding.FragmentHomeBinding;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.Repository;
import com.example.itijavaproject.ui.homescreen.presenter.HomePresenter;
import com.example.itijavaproject.workers.MedReminderUtil;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class HomeFragment extends Fragment implements View.OnClickListener,
        HomeFragInterface, CurrentDayAdapter.HomeAdapterInterface,
        OnDateSelectedListener, HomeCommunicator, HomeDialogCommunicator,
        MaybeObserver<List<Medicine>> {

    private static final String TAG = "HomeFragment.DEV";
    private final String WORKER_TAG = "DAYWORKER";
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1230;
    private NavController navController;
    private FragmentHomeBinding binding;
    private HomePresenter presenter;
    private CurrentDayAdapter adapter;
    private HomeDialog dialog;
    private boolean wantToDelete;
    private Long setSelectedDate = null;
    private Medicine selectedMed = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSelectedDate = System.currentTimeMillis();
        Paper.init(getContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        ViewCompat.requestApplyInsets(binding.parent);
        binding.calendarView.setSelectedDate(CalendarDay.today());
        binding.calendarView.setOnDateChangedListener(this);
        binding.fabAddHealthTacker.setOnClickListener(this);
        binding.fabAddMed.setOnClickListener(this);
        presenter = new HomePresenter(this, Repository
                .getInstance(ConcreteLocalSource.getInstance(this.getContext()), getContext()));
        if (Paper.book().read("isFirstTimeToRun") == null || !Paper.book().read("isFirstTimeToRun").equals("false")) {
            Log.d(TAG, "onViewCreated: paper is not Not shity");
            MedReminderUtil.addDayReminder(12, WORKER_TAG, getContext());
            checkPermission();
            Paper.book().write("isFirstTimeToRun", "false");
        }
        adapter = new CurrentDayAdapter(getContext(), this);
        binding.homeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.homeRecycler.setAdapter(adapter);
        presenter.getSelectedDateMedicines(setSelectedDate);

    }

    @Override
    public void onResume() {
        super.onResume();
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
        medicines.observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClickItemClickListener(Medicine medicine) {
        this.selectedMed = medicine;
        dialog = new HomeDialog(getContext(), this);
        dialog.show(medicine);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget,
                               @NonNull CalendarDay date, boolean selected) {
        LocalDate localDate = LocalDate.ofEpochDay(date.getDate().toEpochDay());
        long toEpochMilli = localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        presenter.getSelectedDateMedicines(toEpochMilli);
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

    @Override
    public void takeMed() {
        selectedMed.setNumOfPills(selectedMed.getNumOfPills() - 1);
        presenter.updateMed(selectedMed);
        dialog.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void snooze() {
        MedReminderUtil.addSingleReminder(5, selectedMed.getMed_id(), getContext());
    }

    @Override
    public void close() {
        dialog.close();
    }

    @Override
    public void showInfo() {
        navController.navigate(HomeFragmentDirections
                .actionHomeFragmentToMedicationDisplayFragment(selectedMed));
        dialog.close();
    }

    @Override
    public void editMed() {
        navController.navigate(HomeFragmentDirections.actionHomeFragmentToAddMedicineFragment(selectedMed));
        dialog.close();
    }

    @Override
    public void deleteMed() {
        if (wantToDelete) {
            presenter.deleteMed(selectedMed);
            dialog.close();
            wantToDelete = false;
            presenter.getSelectedDateMedicines(setSelectedDate);
        } else {
            Toast.makeText(getContext(), "Are you sure u want to delete " + selectedMed.getName()
                    + "!!", Toast.LENGTH_SHORT).show();
            wantToDelete = true;
        }

    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onSuccess(List<Medicine> medicines) {
        Log.d(TAG, "onSuccess: " + medicines.isEmpty());
        adapter.setMedicines(medicines);
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onError: " + e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}