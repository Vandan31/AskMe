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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView txtSignUp,txtForgotPassword;
    EditText edtEmail,edtPassword;
    Button btnSignIn;
    ProgressBar progressBar;
    String strEmail,strPassword;
    FirebaseAuth mAuth;
    String emailPattern="[a-zA=Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtForgotPassword=findViewById(R.id.txtForgotPassword);
        txtSignUp=findViewById(R.id.txtSignUp);
        edtEmail=findViewById(R.id.edtSignInEmail);
        edtPassword= findViewById(R.id.edtSignInPassword);
        btnSignIn=findViewById(R.id.btnSignIn);
        progressBar=findViewById(R.id.signInProgressBar);

        mAuth=FirebaseAuth.getInstance();
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           strEmail= edtEmail.getText().toString();
           strPassword = edtPassword.getText().toString();
           if(isValidate()){
               SignIn();
           }
            }
        });
    }

    private void SignIn() {
        btnSignIn.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(strEmail,strPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent= new Intent(LoginActivity.this,MainActivity2.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Error-"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        btnSignIn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private boolean isValidate(){
        if(TextUtils.isEmpty(strEmail)){
            edtEmail.setError("Email Required");
            return  false;
        }
        if(!strEmail.matches(emailPattern)){
            edtEmail.setError("Enter Valid Email ID");
            return false;
        }
        if(!strPassword.equals(strPassword)){
            edtPassword.setError("Confirm  Password and Password Should be Same");
            return false;
        }
        return true;
    }
}
