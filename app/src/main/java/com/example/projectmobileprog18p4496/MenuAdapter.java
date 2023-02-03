package com.example.projectmobileprog18p4496;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projectmobileprog18p4496.ui.cart.CartFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.Viewholder>{
    ArrayList<MenuModel> listMenus;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReference("addToCart");


    public MenuAdapter(ArrayList<MenuModel> listMenus) {
        this.listMenus = listMenus;
    }

    @NonNull
    @Override
    public MenuAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.menu_item,parent,false);

        MenuAdapter.Viewholder viewholder=new MenuAdapter.Viewholder(view);
        return viewholder;    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.Viewholder holder, int position) {
        MenuModel menuModel=  listMenus.get(position);


        holder.name.setText(menuModel.getName());
        Glide.with(holder.image.getContext()).load(menuModel.getImage()).into(holder.image);

        holder.rating.setText(menuModel.getRating());
        holder.price.setText(menuModel.getPrice());
        holder.availability.setText(menuModel.getAvailability());
        holder.details.setText(menuModel.getDetails());
        if ( holder.availability.getText().equals("Not Available") ) {
            holder.addCart.setClickable(false);
          //  Toast.makeText(holder.itemView.getContext(), "Item Is Not Available ",Toast.LENGTH_SHORT).show();
        }
        else{
       holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qnt=1;

                String saveCurrentDate,saveCurrentTime;
                Calendar date= Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("MM/dd/yyyy");
                saveCurrentDate= currentDate.format(date.getTime());
                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime= currentTime.format(date.getTime());
                String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
               // String id_cart= databaseReference.push().getKey();
                Navigation.findNavController(view).navigate(R.id.action_go);
                HashMap<String,Object> params= new HashMap<>();
                params.put("name",menuModel.getName());
                params.put("image",menuModel.getImage());
                params.put("price",menuModel.getPrice());
                params.put("date",saveCurrentDate);
                params.put("time",saveCurrentTime);
                params.put("quantity",qnt);
                params.put("totalPrice",Integer.valueOf(menuModel.getPrice()));
                params.put("from",menuModel.getFrom());
               // params.put("status"," ");

                databaseReference.child(id).child(menuModel.getName()).setValue(params);

            }
        });}

    }

    @Override
    public int getItemCount() {
        return listMenus.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder  {
        ImageView image;
        TextView name,rating,price,availability,details;
        Button addCart;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.dish_img);
            name=itemView.findViewById(R.id.dish_name);
            rating=itemView.findViewById(R.id.rating_num);
            price=itemView.findViewById(R.id.cash_num);
            availability=itemView.findViewById(R.id.available);
            details=itemView.findViewById(R.id.dish_details);
            addCart=itemView.findViewById(R.id.addCart);




        }


    }

}
