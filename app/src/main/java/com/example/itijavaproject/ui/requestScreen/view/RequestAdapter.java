package com.example.itijavaproject.ui.requestScreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentAddHealthTakerBinding;
import com.example.itijavaproject.databinding.FragmentRegisterBinding;
import com.example.itijavaproject.pojo.model.ListOfRequest;
import com.example.itijavaproject.pojo.model.Request;
import com.example.itijavaproject.ui.medicationsscreen.view.ActiveMedicationAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    ListOfRequest listOfRequest;
    private FragmentRegisterBinding binding;
    private final Context context;

    public RequestAdapter(ListOfRequest listOfRequest, Context context) {
        this.listOfRequest = listOfRequest;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new RequestAdapter.ViewHolder(layoutInflater.inflate(R.layout.custom_row_requests, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtEmailReq.setText(listOfRequest.getRequestList().get(position).getSenderMail());
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.btnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return listOfRequest.getRequestList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtEmailReq;
        Button btnAccept, btnIgnore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmailReq = itemView.findViewById(R.id.txtEmailReq);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnIgnore = itemView.findViewById(R.id.btnIgnore);
        }
    }

}
