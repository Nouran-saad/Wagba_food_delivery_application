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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email =findViewById(R.id.email);
        final EditText password =findViewById(R.id.password);
        final Button SignIn =findViewById(R.id.btnLogin);
        final TextView RegisterNow = findViewById(R.id.RegisterNow);
        final TextView ResetPassword = findViewById(R.id.resetPassword);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(this);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String email_val = email.getText().toString();
                final String password_val = password.getText().toString();


                  /*  FirebaseUser user = firebaseAuth.getCurrentUser();
                    if(user!=null){
                        Intent intent = new Intent(LoginActivity.this, Main_Home.class);
                        startActivity(intent);
                        finish();
                    }*/

                if (email_val.isEmpty() || password_val.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please enter your email or password",Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(email_val, password_val)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressDialog.cancel();
                                    Toast.makeText(LoginActivity.this, "User Login Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, Main_Home.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                }

        });

        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              final String email_val = email.getText().toString();
              progressDialog.setTitle("Sending Mail");
              progressDialog.show();

              firebaseAuth.sendPasswordResetEmail(email_val)
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void unused) {
                              progressDialog.cancel();
                              Toast.makeText(LoginActivity.this,"Email Sent",Toast.LENGTH_SHORT).show();

                          }
                      })
                      .addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              progressDialog.cancel();
                              Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                          }
                      });
            }
        });
       RegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }

}