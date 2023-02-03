package com.example.projectmobileprog18p4496;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmobileprog18p4496.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CheckOut extends AppCompatActivity{

    TextView price;
    RadioGroup radioGrp;
   // RadioButton radioButton;
    Map<String, Object> updates;
    Map<String, Object> update_child;
    Map<String, Object> History;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        price=findViewById(R.id.total_amount);
        radioGrp=findViewById(R.id.radioGroup);
      updates = new HashMap<>();
        History = new HashMap<>();
        update_child = new HashMap<>();
        String price_val =getIntent().getStringExtra("price");
        price.setText(price_val);

        databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://mobile-programming-proje-a093b-default-rtdb.firebaseio.com/");
        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        { String str;
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.time_delivery1:
                        str = "12 pm";
                        break;
                    case R.id.time_delivery2:
                        str = "3 pm";
                        break;

                }
                updates.put("time", str);
            }
        });

    }

    public void goToOrder (View view){



       // updates.put("status", "OrderPlaced");
       // databaseReference.child("orderStatus").updateChildren(updates);

        databaseReference.child("addToCart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener()  {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String key = databaseReference.child("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().getKey();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    History.put("name", dataSnapshot.child("name").getValue().toString());
                    History.put("image", dataSnapshot.child("image").getValue().toString());
                    History.put("price", dataSnapshot.child("price").getValue());
                    History.put("date", dataSnapshot.child("date").getValue().toString());
                    History.put("time", dataSnapshot.child("time").getValue().toString());
                    History.put("quantity", dataSnapshot.child("quantity").getValue());
                    History.put("totalPrice", dataSnapshot.child("totalPrice").getValue());
                    History.put("from", dataSnapshot.child("from").getValue().toString());
                    History.put("status", "WaitingToConfirm");



                    databaseReference.child("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(key).child(dataSnapshot.child("from")
                            .getValue().toString()).child(dataSnapshot.child("name").getValue().toString()).setValue(History);

                    databaseReference.child("orderStatus").child(key).child("orderId").setValue(key);
                    databaseReference.child("orderStatus").child(key).child("status").setValue("WaitingToConfirm");
                    databaseReference.child("orderStatus").child(key).updateChildren(updates);
                }



            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.child("addToCart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();


        Intent intent = new Intent (this, PlaceOrder.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent (this, Main_Home.class);
        startActivity(intent);
        finish();
    }
}