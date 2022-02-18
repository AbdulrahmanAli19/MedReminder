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

public class InactiveMedicationAdapter extends RecyclerView.Adapter<InactiveMedicationAdapter.ViewHolder> {
    private List<Medicine> inactiveMedicines;
    private final Context context;
    ImageView imgIcon;

    public InactiveMedicationAdapter(List<Medicine> inactiveMedicines, Context context) {
        this.inactiveMedicines = inactiveMedicines;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.customrowmeds, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Glide.with(context).load(inactiveMedicines.get(position).getIconType()).apply(new RequestOptions()
//                .override(24, 24)).placeholder(R.drawable.ic_injection__1_)
//                .error(R.drawable.ic_launcher_foreground).into(imgIcon);
        holder.txtMedName.setText(inactiveMedicines.get(position).getName());
        holder.txtMedStrength.setText(inactiveMedicines.get(position).getStrength());
//        holder.txtMedRefill.setText(inactiveMedicines.get(position).getNumOfPills());
    }

    @Override
    public int getItemCount() {
        return inactiveMedicines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMedName;
        TextView txtMedStrength;
        TextView txtMedRefill;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMedName = itemView.findViewById(R.id.txtMedName);
            txtMedStrength = itemView.findViewById(R.id.txtMedStrength);
            txtMedRefill = itemView.findViewById(R.id.txtMedRefill);
            imageView=itemView.findViewById(R.id.imgIcon);
        }
    }
}
