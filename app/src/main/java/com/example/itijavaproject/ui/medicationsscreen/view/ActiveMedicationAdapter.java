package com.example.itijavaproject.ui.medicationsscreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.itijavaproject.R;
import com.example.itijavaproject.pojo.model.Medicine;

import java.util.List;

public class ActiveMedicationAdapter extends RecyclerView.Adapter<ActiveMedicationAdapter.ViewHolder> {
    private List<Medicine>ActiveMedicines;
    private final Context context;
    ImageView imgIcon;
    public ActiveMedicationAdapter(List<Medicine> ActiveMedicines, Context context) {
        this.ActiveMedicines = ActiveMedicines;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View v =layoutInflater.inflate(R.layout.customrowmeds,parent,true);
        ViewHolder viewHolder= new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(ActiveMedicines.get(position).getIconType()).apply(new RequestOptions()
                .override(24,24)).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground).into(imgIcon);
        holder.txtMedName.setText(ActiveMedicines.get(position).getName());
        holder.txtMedStrength.setText(ActiveMedicines.get(position).getStrength());
        holder.txtMedRefill.setText(ActiveMedicines.get(position).getNumOfPills());


    }

    @Override
    public int getItemCount() {
        return ActiveMedicines.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMedName;
        TextView txtMedStrength;
        TextView txtMedRefill;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMedName=itemView.findViewById(R.id.txtMedName);
            txtMedStrength=itemView.findViewById(R.id.txtMedStrength);
            txtMedRefill=itemView.findViewById(R.id.txtMedRefill);
        }
    }

}
