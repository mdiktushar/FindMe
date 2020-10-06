package com.example.findme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class UploadPhoto extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private ImageView profilePhoto;
    private Button upload;
    private FirebaseStorage firebaseStorage;
    private static int IMAGE_REQUEST = 1;
    Uri imagePath;
    private StorageReference storageReference;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        profilePhoto = findViewById(R.id.profileImageUploadViewID);
        upload = findViewById(R.id.uploadPhotoButtonID);
        progressBar = findViewById(R.id.profilePhotoProgressBarID);

        profilePhoto.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profileImageUploadViewID:
                    selectPhoto();
                break;
            case R.id.uploadPhotoButtonID:
                uploadPhoto();
                break;
        }
    }

    private void uploadPhoto() {

        progressBar.setVisibility(View.VISIBLE);
        try {
            StorageReference imageRef = storageReference.child(mAuth.getUid()).child("Image").child("Profile Photo");
            UploadTask uploadTask = imageRef.putFile(imagePath);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Fail to Upload Photo",Toast.LENGTH_LONG).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    finish();
                    Toast.makeText(getApplicationContext(),"Photo Uploaded",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(intent);
                }
            });
        }catch (Exception E){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"Please Select a PNG, JPG, JPEG File",Toast.LENGTH_LONG).show();
        }

    }

    private void selectPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imagePath = data.getData();
            Picasso.get().load(imagePath).into(profilePhoto);

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