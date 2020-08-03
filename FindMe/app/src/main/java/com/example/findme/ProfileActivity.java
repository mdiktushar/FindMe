package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    private ImageView profilePhoto;
    private TextView proName, proEmail, proDescription, proExperience, proPhone, proDateOfBirth, proGender, proAddress, proSchool, proCollage, proUniversity, proSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        proEmail = findViewById(R.id.proEmailID);
        proName = findViewById(R.id.proNameID);
        proDescription = findViewById(R.id.profileDescriptionID);
        proExperience = findViewById(R.id.proExperienceID);
        proPhone = findViewById(R.id.proPhoneID);
        proDateOfBirth = findViewById(R.id.proDateOfBirthID);
        proGender = findViewById(R.id.proGenderID);
        proAddress = findViewById(R.id.proAddressID);
        proSchool = findViewById(R.id.proSchoolID);
        proCollage = findViewById(R.id.proCollageID);
        proUniversity = findViewById(R.id.proUniversityID);
        proSkills = findViewById(R.id.proSkillID);

        profilePhoto = findViewById(R.id.viewProfilePhotoID);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference(mAuth.getUid());

        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(mAuth.getUid()).child("Image/Profile Photo").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(profilePhoto);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.signOunMenuItem){
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }else if(item.getItemId()==R.id.updateDataMenuItem){
            Intent intent = new Intent(getApplicationContext(),UpdateProfileData.class);
            startActivity(intent);
        }else if(item.getItemId()==R.id.userSettingsID){
            Intent intent = new Intent(getApplicationContext(),UserSeatting.class);
            startActivity(intent);
        }else if(item.getItemId()==R.id.photoItem){
            Intent intent = new Intent(getApplicationContext(),PhotoSetting.class);
            startActivity(intent);
        }else if(item.getItemId()==R.id.searchID){
            try {
                Intent intent = new Intent(getApplicationContext(),FindPeople.class);
                startActivity(intent);
            }catch (Exception e){

            }

        }

        return super.onOptionsItemSelected(item);
    }

}