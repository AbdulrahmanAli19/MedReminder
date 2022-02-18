package com.example.itijavaproject.ui.medicationsscreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.itijavaproject.MainActivity;
import com.example.itijavaproject.R;
import com.example.itijavaproject.data.db.ConcretLocalSource;
import com.example.itijavaproject.databinding.FragmentHomeBinding;
import com.example.itijavaproject.databinding.FragmentMedicationsBinding;
import com.example.itijavaproject.pojo.model.Medicine;
import com.example.itijavaproject.pojo.repo.Repository;
import com.example.itijavaproject.ui.homescreen.view.HomeFragmentDirections;
import com.example.itijavaproject.ui.medicationsscreen.presenter.MedicationsPresenter;

import java.util.List;


public class MedicationsFragment extends Fragment implements MedicationViewInterface {

    List<Medicine> medicines;
    RecyclerView RecActive, RecInactive;
    private FragmentMedicationsBinding binding;
    RecyclerView.LayoutManager layoutManager;
    MedicationsPresenter medicationsPresenter;
    ActiveMedicationAdapter activeMedicationAdapter;
    InactiveMedicationAdapter inactiveMedicationAdapter;

    private NavController navController;
    private MedicationsFragment bind;
    Button AddMeds;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddMeds = view.findViewById(R.id.AddMeds);
        String msg = MedicationsFragmentArgs.fromBundle(getArguments()).getName();
        navController = Navigation.findNavController(view);
       /* NavDirections directions = MedicationsFragmentDirections.actionMedicationsFragmentToAddMedicineFragment();
        AddMeds.setOnClickListener(v -> navController
                .navigate(directions));*/
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        RecActive = view.findViewById(R.id.RecActive);
        RecInactive = view.findViewById(R.id.RecInactive);
        RecActive.setLayoutManager(new LinearLayoutManager(getContext()));
        RecInactive.setLayoutManager(new LinearLayoutManager(getContext()));
        medicationsPresenter = new MedicationsPresenter(this, getContext());
        medicationsPresenter.getActiveMed(getViewLifecycleOwner());
        medicationsPresenter.getInactiveMed(getViewLifecycleOwner());


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMedicationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void displayActiveMeds(List<Medicine> activeMeds) {
        activeMedicationAdapter = new ActiveMedicationAdapter(medicines, getContext());
        RecActive.setAdapter(activeMedicationAdapter);
    }

    @Override
    public void displayInactiveMeds(List<Medicine> inactiveMeds) {
        inactiveMedicationAdapter = new InactiveMedicationAdapter(medicines, getContext());
        RecInactive.setAdapter(inactiveMedicationAdapter);
    }
}