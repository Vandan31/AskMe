package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
mAuth=FirebaseAuth.getInstance();
            new Handler().postDelayed(new Runnable() {
                @Override

                public void run() {
                    if(mAuth.getCurrentUser()!=null){
                        startActivity(new Intent(MainActivity.this,MenuActivity.class));
                    }else{
                        startActivity(new Intent(MainActivity.this,MenuActivity.class));
                    }
                  finish();
                }
            },3000);
        }
    }
