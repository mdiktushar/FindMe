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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String newName;
    String newEmail;
    String newDescription;
    String newExperience;
    String newPhone;
    String newDateOfBirth;
    String newGender;
    String newAddress;
    String newSchool;
    String newCollage;
    String newUniversity;
    String newSkills;
    String newImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(mAuth.getUid());

        profilePhoto = findViewById(R.id.profileImageUploadViewID);
        upload = findViewById(R.id.uploadPhotoButtonID);
        progressBar = findViewById(R.id.profilePhotoProgressBarID);


        final DatabaseReference databaseReference = firebaseDatabase.getReference(mAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                newName = userProfile.userName;
                newEmail = userProfile.userEmail;
                newDescription = userProfile.description;
                newExperience = userProfile.experience;
                newPhone = userProfile.phone;
                newDateOfBirth = userProfile.dateOfBirth;
                newGender = userProfile.gender;
                newAddress = userProfile.address;
                newSchool = userProfile.school;
                newCollage = userProfile.collage;
                newUniversity = userProfile.university;
                newSkills = userProfile.skills;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });


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
//            UploadTask uploadTask = imageRef.putFile(imagePath);
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(getApplicationContext(),"Fail to Upload Photo",Toast.LENGTH_LONG).show();
//
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    progressBar.setVisibility(View.GONE);
//                    finish();
//                    Toast.makeText(getApplicationContext(),"Photo Uploaded",Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
//                    startActivity(intent);
//                }
//            });

            imageRef.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    Uri link = uriTask.getResult();
                    //UserProfile userProfile = new UserProfile(link.toString());
                    newImageUri = link.toString();
                    UserProfile userProfile = new UserProfile(newEmail,newName,newDateOfBirth,newGender,newDescription,newExperience,newPhone,newAddress,newSchool,newCollage,newUniversity,newSkills,newImageUri);
                    databaseReference.setValue(userProfile);


                    finish();
                    Toast.makeText(getApplicationContext(),"Photo Uploaded",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Not Uploaded",Toast.LENGTH_LONG).show();
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