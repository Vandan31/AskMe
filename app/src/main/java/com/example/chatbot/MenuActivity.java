package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
Button btnLogin,btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnLogin=findViewById(R.id.btnMainLogin);
        btnRegister=findViewById(R.id.btnMainSignUp);

btnLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent= new Intent(MenuActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
});
btnRegister.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent= new Intent(MenuActivity.this,SignUpActivity.class);
        startActivity(intent);
        finish();
    }
});
    }
}