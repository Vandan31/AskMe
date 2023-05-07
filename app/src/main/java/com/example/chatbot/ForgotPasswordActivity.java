package com.example.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
Button btnReset,btnBack;
EditText edtEmail;
String strEmail;
ProgressBar progressBar;
    String emailPattern="[a-zA=Z0-9._-]+@[a-z]+\\.+[a-z]+";
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        btnReset=findViewById(R.id.btnReset);
        btnBack= findViewById(R.id.btnForgotPasswordBack);
        edtEmail=findViewById(R.id.edtForgotPasswordEmail);
        progressBar=findViewById(R.id.forgetPasswordProgressbar);

        mAuth=FirebaseAuth.getInstance();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail=edtEmail.getText().toString();
                if(isValidate()){
                    ResetPassword();
                }
            }
        });
    }

    private void ResetPassword() {
        btnReset.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(strEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ForgotPasswordActivity.this, "Email Sent Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private boolean isValidate(){
        if(TextUtils.isEmpty(strEmail)){
            edtEmail.setError("Email Required");
            return  false;
        }
        if(strEmail.matches(emailPattern)){
            edtEmail.setError("Enter Valid Email ID");
            return false;
        }

        return true;
    }
}