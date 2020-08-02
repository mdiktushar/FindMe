package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgerPassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView goBackToLogin;
    private EditText forgerPasswordEmail;
    private Button resetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forger_password);

        mAuth = FirebaseAuth.getInstance();

        forgerPasswordEmail = findViewById(R.id.forgerPassowrdEmailID);
        resetPassword = findViewById(R.id.resetPasswordButtonID);
        goBackToLogin = findViewById(R.id.goToLoginID);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = forgerPasswordEmail.getText().toString().trim();

                if(email.equals(null))
                    Toast.makeText(getApplicationContext(),"Please enter your registered email ID",Toast.LENGTH_LONG).show();
                else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Password Reset Email Sent",Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            }else{
                                Toast.makeText(getApplicationContext(),"There is no account with this email!!!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        goBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}