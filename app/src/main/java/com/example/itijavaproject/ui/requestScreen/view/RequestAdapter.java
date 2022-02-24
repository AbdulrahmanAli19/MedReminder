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
import androidx.recyclerview.widget.RecyclerView;

import com.example.itijavaproject.R;
import com.example.itijavaproject.databinding.FragmentRegisterBinding;
import com.example.itijavaproject.pojo.model.ListOfRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {
    private ListOfRequest listOfRequest;
    private FragmentRegisterBinding binding;
    private final Context context;
    private DatabaseReference databaseReference;


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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtEmailReq.setText(listOfRequest.getRequestList().get(position).getSenderMail());
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        holder.btnIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference= FirebaseDatabase.getInstance().getReference("Requests")
                        .child("requestList").push();
               databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren())
                                {
                                    ds.getRef().removeValue();
                                    int tag = (Integer) view.getTag();
                                    if (tag != (listOfRequest.getRequestList().size() - 1)) {
                                        listOfRequest.getRequestList().remove(tag);
                                        Log.d("GCM", "Item removed from " + tag);
//                                        myAdapter.notifyDataSetChanged();
                                    }
                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();

                            }
                        });

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
            txtEmailReq = itemView.findViewById(R.id.txtmsgRefill);
            btnAccept = itemView.findViewById(R.id.btnok);
            btnIgnore = itemView.findViewById(R.id.btnIcancel);
        }
    }

}
