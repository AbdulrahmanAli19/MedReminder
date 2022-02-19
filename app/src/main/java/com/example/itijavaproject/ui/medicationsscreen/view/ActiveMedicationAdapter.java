package com.example.itijavaproject.ui.medicationsscreen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itijavaproject.R;
import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

public class ActiveMedicationAdapter extends RecyclerView.Adapter<ActiveMedicationAdapter.ViewHolder> {
    private List<Medicine> ActiveMedicines;
    private final Context context;
    private final NavController navController;
//    private MedicationsFragment binding;

    public ActiveMedicationAdapter(List<Medicine> ActiveMedicines, NavController navController, Context context) {
        this.ActiveMedicines = ActiveMedicines;
        this.context = context;
        this.navController = navController;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.customrowmeds, parent, false));
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (ActiveMedicines.get(position).getIconType().equals(context.getString(R.string.pill))) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pill));
        } else if (ActiveMedicines.get(position).getIconType().equals(context.getString(R.string.bottle))) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bottle_pill));
        } else if (ActiveMedicines.get(position).getIconType().equals(context.getString(R.string.drop))) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medicine));
        } else if (ActiveMedicines.get(position).getIconType().equals(context.getString(R.string.injection))) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_injection__1_));
        }
        holder.txtMedName.setText(ActiveMedicines.get(position).getName());
        holder.txtMedStrength.setText(ActiveMedicines.get(position).getStrength());
        holder.txtNumStrength.setText("" + ActiveMedicines.get(position).getNoOfStrength());
        holder.constLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(MedicationsFragmentDirections.actionMedicationsFragmentToMedicationDisplayFragment(ActiveMedicines.get(position)));
            }
        });
//        holder.txtMedRefill.setText(ActiveMedicines.get(position).getNumOfPills());
    }

    @Override
    public int getItemCount() {
        return ActiveMedicines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMedName;
        TextView txtMedStrength;
        TextView txtMedRefill;
        TextView txtNumStrength;
        ImageView imageView;
        ConstraintLayout constLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMedName = itemView.findViewById(R.id.txtMedName);
            txtMedStrength = itemView.findViewById(R.id.txtMedStrength);
            txtMedRefill = itemView.findViewById(R.id.txtMedRefill);
            txtNumStrength = itemView.findViewById(R.id.txtNumStrength);
            imageView = itemView.findViewById(R.id.imgIcon);
            constLayout = itemView.findViewById(R.id.ActiveconstLayout);
        }
    }

}
