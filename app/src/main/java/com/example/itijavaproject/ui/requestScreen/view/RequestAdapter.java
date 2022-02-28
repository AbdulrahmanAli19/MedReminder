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
import androidx.recyclerview.widget.RecyclerView;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentRegisterBinding;
import com.example.itijavaproject.pojo.model.ListOfRequest;
import com.example.itijavaproject.pojo.model.Request;
import com.example.itijavaproject.pojo.model.User;
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

//                Query query = databaseReference.child(FirebaseAuth.getInstance().getUid())
//                        .child("recivedRequests").orderByChild("senderUid");
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////                          User user=snapshot.getValue(User.class);
//                        Request request = snapshot.getValue(Request.class);
//                        for (DataSnapshot db : snapshot.getChildren()) {
////                            db.getRef().setValue(request.setState(true));
//                        }
//                        Toast.makeText(context, "accepted", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

            }
        });
        holder.btnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Request request1 = new Request();
                databaseReference = FirebaseDatabase.getInstance().getReference("users");
                Query query = databaseReference.child(FirebaseAuth.getInstance().getUid())
                        .child("recivedRequests").orderByChild("senderMail");
                Log.d(TAG, "onClick query: ");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            dataSnapshot.getRef();
                        }
                        int newPosition = holder.getAdapterPosition();
                        request.remove(position);
                        notifyItemRemoved(newPosition);
                        notifyItemRangeChanged(newPosition, request.size());

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                    }
                });
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
