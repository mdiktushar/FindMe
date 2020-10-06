package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PhotoSetting extends AppCompatActivity implements View.OnClickListener {

    private Button uploadProfilePhoto, uploadCoverPhoto, deleteProfilePhoto, deleteCoverPhoto;
    private FirebaseAuth mAuth;
    private FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference1;


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
    String newImageUri = "https://firebasestorage.googleapis.com/v0/b/find-me-d1c5a.appspot.com/o/16363.png?alt=media&token=763c3e44-55a7-4092-84fd-bba058f58d71";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference1 = firebaseDatabase.getReference(mAuth.getUid());

        databaseReference1.addValueEventListener(new ValueEventListener() {
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


        uploadProfilePhoto = findViewById(R.id.goUploadProfilePhoto);
        uploadCoverPhoto = findViewById(R.id.goUploadCoverPhoto);
        deleteCoverPhoto = findViewById(R.id.deleteCoverPhotoID);
        deleteProfilePhoto = findViewById(R.id.deleteProfilePhotoID);


        uploadProfilePhoto.setOnClickListener(this);
        uploadCoverPhoto.setOnClickListener(this);
        deleteProfilePhoto.setOnClickListener(this);
        deleteCoverPhoto.setOnClickListener(this);

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
            case R.id.deleteProfilePhotoID:
                deleteProfilePhtotTask();
                break;
            case R.id.deleteCoverPhotoID:
                deleteCoverPhotoTask();
                break;
        }
    }

    private void deleteProfilePhtotTask() {
        try {
            StorageReference storageReference = firebaseStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/find-me-d1c5a.appspot.com/o/"+mAuth.getUid()+"%2FImage%2FProfile%20Photo?alt=media&token=f83ba43c-5787-406c-b00b-bd4dd4a76935");
            storageReference.delete();
            Toast.makeText(getApplicationContext(),"Delete Successful",Toast.LENGTH_LONG).show();
            UserProfile userProfile = new UserProfile(newEmail,newName,newDateOfBirth,newGender,newDescription,newExperience,newPhone,newAddress,newSchool,newCollage,newUniversity,newSkills,newImageUri);
            databaseReference1.setValue(userProfile);

            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Nothing to Delete "+e.toString(),Toast.LENGTH_LONG).show();
        }

    }

    private void deleteCoverPhotoTask() {
        try {
            StorageReference storageReference = firebaseStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/find-me-d1c5a.appspot.com/o/"+mAuth.getUid()+"%2FImage%2FCover%20Photo?alt=media&token=243517b4-56ce-425a-8ea0-c6e3b1e449ef");
            storageReference.delete();
            Toast.makeText(getApplicationContext(),"Delete Successful",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
            finish();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Nothing to Delete "+e.toString(),Toast.LENGTH_LONG).show();
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