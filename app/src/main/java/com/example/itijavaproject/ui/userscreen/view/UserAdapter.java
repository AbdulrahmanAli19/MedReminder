package com.example.itijavaproject.ui.userscreen.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itijavaproject.R;
import com.example.itijavaproject.pojo.model.Request;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private static final String TAG = "UserAdapter.dev";
    private List<Request> request = new ArrayList<>();

    private final UserFragInterface userFragInterface;

    public UserAdapter(UserFragInterface userFragInterface) {
        this.userFragInterface = userFragInterface;
    }

    public void setRequest(List<Request> request) {
        Log.d(TAG, "setRequest: called" + request.get(0).getSenderMail());
        this.request.clear();
        this.request.addAll(request);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new UserAdapter.ViewHolder(layoutInflater.inflate(R.layout.custom_row_users, parent, false), userFragInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTackerMail.setText(request.get(position).getSenderMail());
//        holder.imageTacker.setImageResource(R.drawable.img_avatar);

    }

    @Override
    public int getItemCount() {
        return !request.isEmpty() ? request.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTackerMail;
        private ImageView imageTacker;

        public ViewHolder(@NonNull View itemView, UserFragInterface userFragInterface) {
            super(itemView);
            txtTackerMail = itemView.findViewById(R.id.txtTackerMail);
            imageTacker = itemView.findViewById(R.id.profile_image);
            itemView.findViewById(R.id.card).setOnClickListener(v -> userFragInterface
                    .onItemClick(getAdapterPosition(), request.get(getAdapterPosition())));
        }
    }

    interface UserFragInterface {
        void onItemClick(int pos, Request selectedReg);
    }
}
