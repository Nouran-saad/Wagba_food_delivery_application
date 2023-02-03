package com.example.projectmobileprog18p4496;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    ArrayList<HistoryModel> listHistory;
    DatabaseReference databaseReference;
    public HistoryAdapter(ArrayList<HistoryModel> listHistory) {
        this.listHistory = listHistory;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.history_item,parent,false);
        databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://mobile-programming-proje-a093b-default-rtdb.firebaseio.com/");
        HistoryAdapter.ViewHolder viewholder=new HistoryAdapter.ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        HistoryModel historyModel=  listHistory.get(position);
        holder.name.setText(historyModel.getName());
        Glide.with(holder.image.getContext()).load(historyModel.getImage()).into(holder.image);
        holder.date.setText(historyModel.getDate());

        holder.quantity.setText(String.valueOf(historyModel.getQuantity()));
        holder.totalPrice.setText(String.valueOf(historyModel.getTotalPrice()));
        holder.from.setText(historyModel.getFrom());


        databaseReference.child("orderStatus").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
              String  status_val =dataSnapshot.child("status").getValue().toString();
                 holder.status.setText(status_val);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView date,name,time,status,quantity,totalPrice,from;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.img_history);
            name=itemView.findViewById(R.id.history_rest);
            date=itemView.findViewById(R.id.date_history);

            status=itemView.findViewById(R.id.history_status);
            quantity=itemView.findViewById(R.id.details_order2_amt);
            totalPrice=itemView.findViewById(R.id.details_order);
            from=itemView.findViewById(R.id.from_val);
        }
    }
}
