package com.example.projectmobileprog18p4496;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectmobileprog18p4496.ui.cart.CartFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<CartModel> listCarts;
    UpdatePrice listener;
    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReference("addToCart").child(id);
    public CartAdapter(ArrayList<CartModel> listCarts,  UpdatePrice listener) {

        this.listCarts = listCarts;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.mycart_item,parent,false);

        CartAdapter.ViewHolder viewholder=new CartAdapter.ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel cartModel = listCarts.get(position);

        holder.name.setText(cartModel.getName());
        Glide.with(holder.image.getContext()).load(cartModel.getImage()).into(holder.image);
        holder.price.setText(cartModel.getPrice());

        holder.quantity.setText(String.valueOf(cartModel.getQuantity()));

        holder.increase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                int qnt = cartModel.getQuantity();
                qnt++;
                clearProductList();
                Map<String, Object> updates = new HashMap<String,Object>();
                int totalprice = Integer.valueOf(cartModel.getPrice());
                 int oneProductPrice = (totalprice) * cartModel.getQuantity();
                totalprice = totalprice + oneProductPrice;
                updates.put("quantity",qnt );
                updates.put("totalPrice", totalprice);


                databaseReference.child(cartModel.getName()).updateChildren(updates);



            }
        });






        holder.decrease.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int qnt = cartModel.getQuantity();
                qnt--;
                if (qnt == 0){
                    databaseReference.child(cartModel.getName()).removeValue();
                    listCarts.remove(position);
                    notifyItemRemoved(position);
                }
                else {
                    clearProductList();
                    Map<String, Object> updates = new HashMap<String, Object>();
                    int totalprice = Integer.valueOf(cartModel.getPrice());
                    int oneProductPrice = (totalprice) * cartModel.getQuantity();
                    totalprice = oneProductPrice - totalprice;
                    updates.put("quantity", qnt);
                    updates.put("totalPrice", totalprice);


                    databaseReference.child(cartModel.getName()).updateChildren(updates);



                }
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                   String total = String.valueOf(ds.child("totalPrice").getValue());
                     count = count + Integer.valueOf(total);

                }
                listener.setText(String.valueOf(count));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    @Override
    public int getItemCount() {
        return listCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,price;
        EditText quantity;
        Button increase,decrease;
        //int count=0;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.cartImg);
            name=itemView.findViewById(R.id.name_card);
            price=itemView.findViewById(R.id.cart_cash);
            quantity=itemView.findViewById(R.id.quant_number);

              increase= itemView.findViewById(R.id.increase);
              decrease=  itemView.findViewById(R.id.decrease);

        }
    }
    public void clearProductList(){
        listCarts.clear();
    }

}
