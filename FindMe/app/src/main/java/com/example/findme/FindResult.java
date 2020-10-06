package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class FindResult extends AppCompatActivity implements View.OnClickListener {

    //private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    private ImageView profilePhoto, coverPhoto;
    private TextView proName, proEmail, proDescription, proExperience, proPhone, proDateOfBirth, proGender, proAddress, proSchool, proCollage, proUniversity, proSkills;
    private String userID;
    private TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //mAuth = FirebaseAuth.getInstance();
        try {


            Bundle bundle = getIntent().getExtras();
            userID = bundle.getString("UserKey");

            proEmail = findViewById(R.id.fproEmailID);
            proName = findViewById(R.id.fproNameID);
            proDescription = findViewById(R.id.fprofileDescriptionID);
            proExperience = findViewById(R.id.fproExperienceID);
            proPhone = findViewById(R.id.fproPhoneID);
            proDateOfBirth = findViewById(R.id.fproDateOfBirthID);
            proGender = findViewById(R.id.fproGenderID);
            proAddress = findViewById(R.id.fproAddressID);
            proSchool = findViewById(R.id.fproSchoolID);
            proCollage = findViewById(R.id.fproCollageID);
            proUniversity = findViewById(R.id.fproUniversityID);
            proSkills = findViewById(R.id.fproSkillID);
            coverPhoto = findViewById(R.id.viewCoverPhotoID);
            id = findViewById(R.id.resUidID);

            id.setOnClickListener(this);

            profilePhoto = findViewById(R.id.fviewProfilePhotoID);

            //mAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseStorage = FirebaseStorage.getInstance();

            try {
                DatabaseReference databaseReference = firebaseDatabase.getReference(userID);

                StorageReference storageReference = firebaseStorage.getReference();
                storageReference.child(userID).child("Image/Profile Photo").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().into(profilePhoto);
                    }
                });

                storageReference.child(userID).child("Image/Cover Photo").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(coverPhoto);
                    }
                });

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                        proName.setText(userProfile.userName);
                        proEmail.setText(userProfile.userEmail);
                        proDescription.setText(userProfile.description);
                        proExperience.setText(userProfile.experience);
                        proPhone.setText(userProfile.phone);
                        proDateOfBirth.setText(userProfile.dateOfBirth);
                        proGender.setText(userProfile.gender);
                        proAddress.setText(userProfile.address);
                        proSchool.setText(userProfile.school);
                        proCollage.setText(userProfile.collage);
                        proUniversity.setText(userProfile.university);
                        proSkills.setText(userProfile.skills);
                        id.setText(userID);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.getCode(), Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Person Not Found", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Person Not Found", Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.resUidID)
            copyText();
    }

    private void copyText() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("EditText",id.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(),"Copied",Toast.LENGTH_LONG).show();
    }
}