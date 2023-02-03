package com.example.projectmobileprog18p4496;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

   FirebaseAuth firebaseAuth;
   FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText email =findViewById(R.id.email);
        final EditText password =findViewById(R.id.password);
        final EditText userName =findViewById(R.id.username);
        final EditText phoneNumber = findViewById(R.id.PhoneNumber);

        final Button SignUp =findViewById(R.id.btnRegister);
        final TextView LoginNow = findViewById(R.id.Loginow);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        progressDialog= new ProgressDialog(this);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName_val = userName.getText().toString();
                final String phoneNumber_val = phoneNumber.getText().toString();
                final String email_val = email.getText().toString();
                final String password_val = password.getText().toString();

                if (email_val.isEmpty() || password_val.isEmpty() ||userName_val.isEmpty() || phoneNumber_val.isEmpty() ){
                    Toast.makeText(RegisterActivity.this,"Please fill all your information",Toast.LENGTH_SHORT).show();

                }
                else{

                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email_val,password_val)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                progressDialog.cancel();

                                firebaseFirestore.collection("User")
                                        .document(FirebaseAuth.getInstance().getUid())
                                        .set(new UserModel(userName_val,email_val,phoneNumber_val));

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();

                            }
                        });}

            }
        });

        LoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });
    }



}