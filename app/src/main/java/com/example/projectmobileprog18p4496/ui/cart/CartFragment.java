package com.example.projectmobileprog18p4496.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobileprog18p4496.CartAdapter;
import com.example.projectmobileprog18p4496.CartModel;
import com.example.projectmobileprog18p4496.CheckOut;
import com.example.projectmobileprog18p4496.R;
import com.example.projectmobileprog18p4496.RestaurantModel;
import com.example.projectmobileprog18p4496.UpdatePrice;
import com.example.projectmobileprog18p4496.databinding.FragmentCartBinding;
import com.example.projectmobileprog18p4496.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CartFragment extends Fragment implements UpdatePrice {
    ArrayList<CartModel> cartModels=new ArrayList<>();
    private FragmentCartBinding binding;
    RecyclerView recyclerViewCart;
    DatabaseReference databaseReference;
    TextView total_price;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       // CartViewModel cartViewModel =
               // new ViewModelProvider(this).get(CartViewModel.class);

        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewCart=binding.mycartRecycler;
        total_price=binding.totalAmount;

        databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://mobile-programming-proje-a093b-default-rtdb.firebaseio.com/");


        CartAdapter cartAdapter=new CartAdapter(cartModels,this);
        recyclerViewCart.setAdapter(cartAdapter);
        databaseReference.child("addToCart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CartModel model = dataSnapshot.getValue(CartModel.class);
                    cartModels.add(model);
                }

                cartAdapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        //String priceAmount = getArguments().getString("totalPrice");
        //total_price.setText(priceAmount);

        binding.btnCheck.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent (getActivity(), CheckOut.class);
        intent.putExtra("price",total_price.getText().toString());
        startActivity(intent);
    }
});
       // CartViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void setText(String text) {

       total_price.setText(text);
    }
}