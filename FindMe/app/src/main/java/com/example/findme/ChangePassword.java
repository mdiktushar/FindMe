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

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {

    private Button changePassword;
    private EditText newPasswordOne, newPasswordTwo;
    private TextView goBack;
    String password,password2;

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        changePassword = findViewById(R.id.changePasswordID);
        newPasswordOne = findViewById(R.id.newPasswordID1);
        newPasswordTwo = findViewById(R.id.newPasswordID2);
        goBack = findViewById(R.id.goBackID);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        goBack.setOnClickListener(this);
        changePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changePasswordID:
                checkPassword();
                break;
            case R.id.goBackID:
                goBackToProfile();
                break;
        }
    }

    private void passwordChange() {
        firebaseUser.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Password Changed",Toast.LENGTH_LONG).show();
                    goBackToProfile();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Password Not Changed, "+task.getException().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void checkPassword() {
        password = newPasswordOne.getText().toString().trim();
        password2 = newPasswordTwo.getText().toString().trim();

        if(password.isEmpty())
        {
            newPasswordOne.setError("Enter a password");
            newPasswordOne.requestFocus();
            return;
        }
        if(password2.isEmpty())
        {
            newPasswordTwo.setError("Re-enter the password");
            newPasswordTwo.requestFocus();
            return;
        }
        if (!password.equals(password2)) {
            newPasswordTwo.setError("Password does not match");
            newPasswordTwo.requestFocus();
            return;
        }

        passwordChange();
    }

    private void goBackToProfile() {
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
