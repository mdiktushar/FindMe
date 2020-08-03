package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PhotoSetting extends AppCompatActivity implements View.OnClickListener {

    private Button uploadProfilePhoto, uploadCoverPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        uploadProfilePhoto = findViewById(R.id.goUploadProfilePhoto);
        uploadCoverPhoto = findViewById(R.id.goUploadCoverPhoto);


        uploadProfilePhoto.setOnClickListener(this);
        uploadCoverPhoto.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goUploadProfilePhoto:
                Intent intent = new Intent(getApplicationContext(),UploadPhoto.class);
                startActivity(intent);
                break;
            case  R.id.goUploadCoverPhoto:
                Intent intent1 = new Intent(getApplicationContext(),UploadCoverPhoto.class);
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