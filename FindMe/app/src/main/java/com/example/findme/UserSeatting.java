package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class UserSeatting extends AppCompatActivity implements View.OnClickListener {

    private Button emailUpdate, passwordUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_seatting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        emailUpdate = findViewById(R.id.goUpdateEmail);
        passwordUpdate = findViewById(R.id.goUpdatePasswrd);


        emailUpdate.setOnClickListener(this);
        passwordUpdate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goUpdateEmail:
                Intent intent = new Intent(getApplicationContext(),UpdateEmail.class);
                startActivity(intent);
                break;
            case  R.id.goUpdatePasswrd:
                Intent intent1 = new Intent(getApplicationContext(),ChangePassword.class);
                startActivity(intent1);
                break;
        }
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