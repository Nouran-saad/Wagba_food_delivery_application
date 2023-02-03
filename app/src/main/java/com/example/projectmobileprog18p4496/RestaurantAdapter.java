package com.example.projectmobileprog18p4496;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.Viewholder>{
    UpdateMenu updateMenu;

    ArrayList<RestaurantModel> listRestaurants;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://mobile-programming-proje-a093b-default-rtdb.firebaseio.com/").child("Meals");

    public RestaurantAdapter(UpdateMenu updateMenu, ArrayList<RestaurantModel> listRestaurants) {
        this.updateMenu = updateMenu;
        this.listRestaurants = listRestaurants;
    }
    boolean check;
    boolean select=true;
    int row_index=-1;
    @NonNull
    @Override
    public RestaurantAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.item_restaurants,parent,false);

        Viewholder viewholder=new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        RestaurantModel RestaurantModel=  listRestaurants.get(position);
        holder.name.setText(RestaurantModel.getName());
        holder.timing.setText(RestaurantModel.getTiming());
        Glide.with(holder.image.getContext()).load(RestaurantModel.getImage()).into(holder.image);


        if (check){
            ArrayList<MenuModel> MenuModels=new ArrayList<>();


            databaseReference.child("Meals_Restaurants0").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                        MenuModel model = dataSnapshot.getValue(MenuModel.class);
                        MenuModels.add(model);

                    }
                    updateMenu.Call_Menu(holder.name.getText().toString(),MenuModels);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




            check=false;}
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    row_index = position;
                    notifyDataSetChanged();
                    if (holder.name.getText().toString().equals("City Crepe"))  {
                        ArrayList<MenuModel> MenuModels = new ArrayList<>();

                        databaseReference.child("Meals_Restaurants0").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                                    MenuModel model = dataSnapshot.getValue(MenuModel.class);
                                    MenuModels.add(model);
                                }
                                updateMenu.Call_Menu(holder.name.getText().toString(), MenuModels);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("error",error.getMessage());

                            }
                        });

                    } else if (holder.name.getText().toString().equals("Pizza Hut")) {
                        ArrayList<MenuModel> MenuModels = new ArrayList<>();


                        databaseReference.child("Meals_Restaurants1").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                                    MenuModel model = dataSnapshot.getValue(MenuModel.class);
                                    MenuModels.add(model);
                                }
                                updateMenu.Call_Menu(holder.name.getText().toString(), MenuModels);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        /*MenuModels.add(new MenuModel(R.drawable.burger, "Burger1", "4", "70 EGP","Not Available"));
                        MenuModels.add(new MenuModel(R.drawable.burger, "Burger2", "5", "80 EGP","Available"));
                        MenuModels.add(new MenuModel(R.drawable.burger, "Burger3", "3.5", "90 EGP","Not Available"));
                        MenuModels.add(new MenuModel(R.drawable.burger, "Burger4", "4.5", "100 EGP","Available"));*/



                    }  else if (holder.name.getText().toString().equals("Papa Jones")) {
                        ArrayList<MenuModel> MenuModels = new ArrayList<>();

                        databaseReference.child("Meals_Restaurants2").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                                    MenuModel model = dataSnapshot.getValue(MenuModel.class);
                                    MenuModels.add(model);
                                }
                                updateMenu.Call_Menu(holder.name.getText().toString(), MenuModels);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else if (holder.name.getText().toString().equals("Mo'men")) {
                        ArrayList<MenuModel> MenuModels = new ArrayList<>();

                        databaseReference.child("Meals_Restaurants3").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                                    MenuModel model = dataSnapshot.getValue(MenuModel.class);
                                    MenuModels.add(model);
                                }
                                updateMenu.Call_Menu(holder.name.getText().toString(), MenuModels);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else if (holder.name.getText().toString().equals("Cook Door")) {
                        ArrayList<MenuModel> MenuModels = new ArrayList<>();

                        databaseReference.child("Meals_Restaurtants4").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    MenuModel model = dataSnapshot.getValue(MenuModel.class);
                                    MenuModels.add(model);
                                }
                                updateMenu.Call_Menu(holder.name.getText().toString(), MenuModels);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    else if (holder.name.getText().toString().equals("Heart Attack")) {
                        ArrayList<MenuModel> MenuModels = new ArrayList<>();

                        databaseReference.child("Meals_Restaurants5").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    MenuModel model = dataSnapshot.getValue(MenuModel.class);
                                    MenuModels.add(model);
                                }
                                updateMenu.Call_Menu(holder.name.getText().toString(), MenuModels);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    else if (holder.name.getText().toString().equals("KFC")) {
                        ArrayList<MenuModel> MenuModels = new ArrayList<>();

                        databaseReference.child("Meals_Restaurants6").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    MenuModel model = dataSnapshot.getValue(MenuModel.class);
                                    MenuModels.add(model);
                                }
                                updateMenu.Call_Menu(holder.name.getText().toString(), MenuModels);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    else if (holder.name.getText().toString().equals("McDonald's")) {
                        ArrayList<MenuModel> MenuModels = new ArrayList<>();

                        databaseReference.child("Meals_Restaurants7").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    MenuModel model = dataSnapshot.getValue(MenuModel.class);
                                    MenuModels.add(model);
                                }
                                updateMenu.Call_Menu(holder.name.getText().toString(), MenuModels);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    else if (holder.name.getText().toString().equals("Etoile")) {
                        ArrayList<MenuModel> MenuModels = new ArrayList<>();

                        databaseReference.child("Meals_Restaurants8").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    MenuModel model = dataSnapshot.getValue(MenuModel.class);
                                    MenuModels.add(model);
                                }
                                updateMenu.Call_Menu(holder.name.getText().toString(), MenuModels);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else if (holder.name.getText().toString().equals("Qasr El mandy")) {
                        ArrayList<MenuModel> MenuModels = new ArrayList<>();

                        databaseReference.child("Meals_Restaurants9").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    MenuModel model = dataSnapshot.getValue(MenuModel.class);
                                    MenuModels.add(model);
                                }
                                updateMenu.Call_Menu(holder.name.getText().toString(), MenuModels);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            });
            if(select){
                if (position==0){
                    holder.cardView.setBackgroundResource(R.drawable.selected_bk);
                    select=false;
                }
            }
            else {
                if (row_index == position){
                    holder.cardView.setBackgroundResource(R.drawable.selected_bk);
                } else {
                    holder.cardView.setBackgroundResource(R.drawable.unselected_bk);
                }
            }

        }


    @Override
    public int getItemCount() {
        return listRestaurants.size();
    }
    public void filterList(ArrayList<RestaurantModel> filteredList){
        listRestaurants=filteredList;
        notifyDataSetChanged();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name,timing;
        CardView cardView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.restaurantImg) ;
            name=itemView.findViewById(R.id.restaurantName2) ;
            timing =itemView.findViewById(R.id.restaurantTime2) ;
            cardView =itemView.findViewById(R.id.card_view) ;

        }
    }



}
