package com.example.projectmobileprog18p4496;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.projectmobileprog18p4496.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlaceOrder extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    TextView status_one;
    TextView status_two;
    TextView status_three;
    ImageView point2;
    ImageView point3;
    ProgressBar progress1;
    ProgressBar progress2;
    TextView title;
    TextView title2;
    TextView confirm;
    ConstraintLayout layout_track;

    Map<String, Object> History;
    Map<String, Object> update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        History = new HashMap<>();
        update = new HashMap<>();

        layout_track=findViewById(R.id.layout_track);
        confirm= findViewById(R.id.confirm);

        title = findViewById(R.id.title);
        title2 = findViewById(R.id.title2);

        status_one = findViewById(R.id.txt1);
        status_two = findViewById(R.id.txt2);
        status_three = findViewById(R.id.txt3);

        point2 = findViewById(R.id.point3);
        point3 = findViewById(R.id.point4);

        progress1= findViewById(R.id.progress1);
        progress2= findViewById(R.id.progress2);
        databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://mobile-programming-proje-a093b-default-rtdb.firebaseio.com/");

        databaseReference2 = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://mobile-programming-proje-a093b-default-rtdb.firebaseio.com/");


        title.setVisibility(View.INVISIBLE);
        title2.setVisibility(View.INVISIBLE);
        status_one.setVisibility(View.INVISIBLE);
        status_two.setVisibility(View.INVISIBLE);
        status_three.setVisibility(View.INVISIBLE);
        layout_track.setVisibility(View.INVISIBLE);
        DatabaseReference lastRef= databaseReference.child("orderStatus");
        Query lastQuery = lastRef.orderByKey().limitToLast(1);
        lastQuery.addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //snapshot
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String status = dataSnapshot.child("status").getValue(String.class);

                    // long status = snapshot.getChildrenCount();
                    String orderId = dataSnapshot.child("orderId").getValue(String.class);
                    //confirm.setText(String.valueOf(status));

                    if (Objects.equals(status, "WaitingToConfirm")) {
                        confirm.setText(status);
                        layout_track.setVisibility(View.INVISIBLE);
                    }
                   else if (Objects.equals(status, "OrderPlaced")){
                        layout_track.setVisibility(View.VISIBLE);
                        title.setVisibility(View.VISIBLE);
                        title2.setVisibility(View.VISIBLE);
                        title2.setText(orderId);
                        status_one.setText(status);
                        status_one.setVisibility(View.VISIBLE);
                        confirm.setVisibility(View.INVISIBLE);
}
                    else if (Objects.equals(status, "OnTheWay")){
                        layout_track.setVisibility(View.VISIBLE);
                        title.setVisibility(View.VISIBLE);
                        title2.setVisibility(View.VISIBLE);
                        title2.setText(orderId);
                        status_two.setText(status);
                        status_one.setVisibility(View.VISIBLE);
                        status_two.setVisibility(View.VISIBLE);
                        point2.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        DrawableCompat.setTint(progress1.getProgressDrawable(),getResources().getColor(R.color.green));
                        confirm.setVisibility(View.INVISIBLE);

                    }
                    else if (Objects.equals(status, "Delivered")){
                        layout_track.setVisibility(View.VISIBLE);
                        title.setVisibility(View.VISIBLE);
                        title2.setVisibility(View.VISIBLE);
                        title2.setText(orderId);
                        status_three.setText(status);
                        status_one.setVisibility(View.VISIBLE);
                        status_two.setVisibility(View.VISIBLE);
                        status_three.setVisibility(View.VISIBLE);
                        point2.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        point3.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        DrawableCompat.setTint(progress1.getProgressDrawable(),getResources().getColor(R.color.green));
                        DrawableCompat.setTint(progress2.getProgressDrawable(),getResources().getColor(R.color.green));
                        confirm.setVisibility(View.INVISIBLE);

                    }
            //    }
                }

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent (this, Main_Home.class);
        startActivity(intent);
        finish();
    }

    public void goToHome(View view) {

        databaseReference.child("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener()  {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              //  String key = databaseReference.child("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().getKey();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //String val1=dataSnapshot.getValue().toString();

                        for (DataSnapshot ds : dataSnapshot.getChildren())
                        {   //  String val2=ds.getValue().toString();
                            for (DataSnapshot i : ds.getChildren()) {
                                History.put("name", i.child("name").getValue().toString());
                                History.put("image", i.child("image").getValue().toString());
                                History.put("price", i.child("price").getValue());
                                History.put("date", i.child("date").getValue().toString());
                                History.put("time", i.child("time").getValue().toString());
                                History.put("quantity", i.child("quantity").getValue());
                                History.put("totalPrice", i.child("totalPrice").getValue());
                                History.put("from", i.child("from").getValue().toString());

                                databaseReference.child("History").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(i.child("name").getValue().toString()).setValue(History);

                            }
                        }
                    }



                }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("orderStatus").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener()  {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                 String status=   dataSnapshot.child("status").getValue().toString();
                 databaseReference.child("History").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot2) {
                         for (DataSnapshot dataSnapshot2 : snapshot2.getChildren()){
                             dataSnapshot2.child("status").getRef().setValue(status);
                         }
                 }
                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = new Intent (this, Main_Home.class);
        startActivity(intent);
        finish();
    }
}