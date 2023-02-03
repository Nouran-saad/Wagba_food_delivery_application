package com.example.projectmobileprog18p4496.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.projectmobileprog18p4496.CartModel;
import com.example.projectmobileprog18p4496.HistoryAdapter;
import com.example.projectmobileprog18p4496.HistoryModel;
import com.example.projectmobileprog18p4496.R;
import com.example.projectmobileprog18p4496.databinding.FragmentHistoryBinding;
import com.example.projectmobileprog18p4496.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    ArrayList<HistoryModel> historyModels=new ArrayList<>();
    private FragmentHistoryBinding binding;
    RecyclerView recyclerViewHistory;
    DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerViewHistory=binding.myHistoryRecycler;
        databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://mobile-programming-proje-a093b-default-rtdb.firebaseio.com/");


        HistoryAdapter historyAdapter=new HistoryAdapter(historyModels);
        recyclerViewHistory.setAdapter(historyAdapter);
databaseReference.child("History").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    HistoryModel model = dataSnapshot.getValue(HistoryModel.class);
                    historyModels.add(model);


                }

                historyAdapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}