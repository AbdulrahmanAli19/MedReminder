package com.example.itijavaproject.ui.requestScreen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentRegisterBinding;
import com.example.itijavaproject.pojo.model.ListOfRequest;
import com.example.itijavaproject.pojo.model.Request;
import com.example.itijavaproject.pojo.model.User;
import com.example.itijavaproject.ui.medicationsscreen.view.MedicationsFragmentDirections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    private List<Request> request;
    private FragmentRegisterBinding binding;
    private final Context context;
    private DatabaseReference databaseReference;
    private NavController navController;


    private static final String TAG = "RequestAdapter";

    public RequestAdapter(List<Request> request, Context context) {
        this.request = request;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new RequestAdapter.ViewHolder(layoutInflater.inflate(R.layout.custom_row_requests, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtEmailReq.setText(request.get(position).getSenderMail());
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("users");
                DatabaseReference query = databaseReference.child(FirebaseAuth.getInstance().getUid())
                        .child("recivedRequests");
                query.child(request.get(position).getSenderMail().split("\\.")[0]).child("state").setValue("true");
                Log.d(TAG, "onClick query: ");
                int newPosition = holder.getAdapterPosition();
                request.remove(position);
                notifyItemRemoved(newPosition);
                notifyItemRangeChanged(newPosition, request.size());
                navController.navigate(RequestFragmentDirections.actionRequestFragmentToUserFragment());

            }
        });
        holder.btnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("users");
                DatabaseReference query = databaseReference.child(FirebaseAuth.getInstance().getUid())
                        .child("recivedRequests");
                query.child(request.get(position).getSenderMail().split("\\.")[0]).removeValue();
                Log.d(TAG, "onClick query: ");
                int newPosition = holder.getAdapterPosition();
                request.remove(position);
                notifyItemRemoved(newPosition);
                notifyItemRangeChanged(newPosition, request.size());

            }
        });
    }


    @Override
    public int getItemCount() {
        return request.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtEmailReq;
        Button btnAccept, btnIgnore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmailReq = itemView.findViewById(R.id.txtmsgRefill);
            btnAccept = itemView.findViewById(R.id.btnok);
            btnIgnore = itemView.findViewById(R.id.btnIcancel);
        }
    }

}
