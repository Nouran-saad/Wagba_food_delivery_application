package com.example.projectmobileprog18p4496;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private  static  int SPLASH_SCREEN =3500;
    ImageView imageView;
    TextView textView1;
    Animation top, down;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        textView1 = findViewById(R.id.textView);

        top = AnimationUtils.loadAnimation(this, R.anim.top);
        down = AnimationUtils.loadAnimation(this, R.anim.down);
        imageView.setAnimation(top);
        textView1.setAnimation(down);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}