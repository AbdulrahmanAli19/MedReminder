package com.example.itijavaproject.ui.userscreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itijavaproject.R;
import com.example.itijavaproject.pojo.model.Request;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<Request> request;
    private final Context context;

    public UserAdapter(List<Request> request, Context context) {
        this.request = request;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new UserAdapter.ViewHolder(layoutInflater.inflate(R.layout.ciustom_row_users, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTacker.setText(request.get(position).getSenderMail());
        holder.imageTacker.setImageResource(R.drawable.img_avatar);
    }

    @Override
    public int getItemCount() {
        return request.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTacker;
        ImageView imageTacker;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTacker = itemView.findViewById(R.id.txtTacker);
            imageTacker=itemView.findViewById(R.id.imageTacker);


        }
    }
}
