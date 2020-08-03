package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateEmail extends AppCompatActivity implements View.OnClickListener {

    private EditText newEmail;
    private Button updateEmail;

    private String email;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);

        newEmail = findViewById(R.id.editTextTextEmailAddress);
        updateEmail = findViewById(R.id.updateUserEmailID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

        updateEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.updateUserEmailID:
                checkEmailAddress();
        }
    }

    private void checkEmailAddress() {

        email = newEmail.getText().toString().trim();

        if(email.isEmpty())
        {
            newEmail.setError("Enter an email address");
            newEmail.requestFocus();
            return;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            newEmail.setError("Enter a valid email address");
            newEmail.requestFocus();
            return;
        }

        updateEmailAddress();

    }

    private void updateEmailAddress() {

//        firebaseUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                  Toast.makeText(getApplicationContext(),"Email Updated",Toast.LENGTH_LONG).show();
//                    //sendEmailVerification();
//                    goBackToProfile();
//                    finish();
//                }
//                else {
//                    Toast.makeText(getApplicationContext(),"Email Already Used",Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        firebaseUser.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    //Toast.makeText(getApplicationContext(),"Email Updated",Toast.LENGTH_LONG).show();
                    sendEmailVerification();
                    goBackToStart();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Email Already Used "+email,Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(getApplicationContext(),"Successfully Updated, Verification mail has been send!",Toast.LENGTH_LONG).show();
                        mAuth.signOut();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Verification male has'nt been send! : "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void goBackToStart() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}