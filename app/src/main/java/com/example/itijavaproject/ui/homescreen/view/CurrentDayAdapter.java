package com.example.itijavaproject.ui.homescreen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.LayoutHomeBinding;
import com.example.itijavaproject.pojo.model.Medicine;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.text.SimpleDateFormat;
import java.util.List;

public class CurrentDayAdapter extends RecyclerView.Adapter<CurrentDayAdapter.CurrentDayViewHolder> {

    private List<Medicine> medicines;
    private final Context context;
    private final HomeAdapterInterface homeAdapterInterface;

    public CurrentDayAdapter(Context context, HomeAdapterInterface homeAdapterInterface) {
        this.context = context;
        this.homeAdapterInterface = homeAdapterInterface;
    }

    @NonNull
    @Override
    public CurrentDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CurrentDayViewHolder(LayoutHomeBinding.inflate(LayoutInflater.from(context),
                parent, false), homeAdapterInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CurrentDayViewHolder holder, int position) {
        Medicine currentMed = medicines.get(position);
        holder.binding.imgMed.setImageDrawable(getImage(currentMed.getIconType()));
        holder.binding.txtMedName.setText(currentMed.getName());
        holder.binding.txtTime.setText(getTime(currentMed.getTimes().get(0)));
        String details = currentMed.getNoOfStrength() + " " + currentMed.getStrength() + currentMed.getStrength()
                + ", take " + currentMed.getTimes().size() + "(s)";
        holder.binding.txtMedDetails.setText(details);
    }

    @Override
    public int getItemCount() {
        return medicines != null ? medicines.size() : 0;
    }

    private String getTime(Long date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new
                SimpleDateFormat(" hh:mm a");
        return simpleDateFormat.format(date);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
        notifyDataSetChanged();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private Drawable getImage(String img) {
        if (img.equals(context.getString(R.string.pill))) {

            return context.getResources().getDrawable(R.drawable.ic_pill);
        } else if (img.equals(context.getString(R.string.bottle))) {

            return context.getResources().getDrawable(R.drawable.ic_bottle_pill);
        } else if (img.equals(context.getString(R.string.drop))) {

            return context.getResources().getDrawable(R.drawable.ic_medicine);
        } else if (img.equals(context.getString(R.string.injection))) {

            return context.getResources().getDrawable(R.drawable.ic_injection__1_);
        } else {

            return null;
        }
    }

    class CurrentDayViewHolder extends RecyclerView.ViewHolder {
        private final LayoutHomeBinding binding;

        public CurrentDayViewHolder(LayoutHomeBinding binding, HomeAdapterInterface homeAdapterInterface) {
            super(binding.getRoot());
            this.binding = binding;

            binding.cardView.setOnClickListener(v -> homeAdapterInterface
                    .onClickItemClickListener(medicines.get(getAdapterPosition())));
        }
    }

    interface HomeAdapterInterface {
        void onClickItemClickListener(Medicine medicine);
    }
}
