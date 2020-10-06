package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileData extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;

    private EditText updateName, updateEmail, updateDescription, updateExperience, updatePhone, updateDateOfBirth, updateGender, updateAddress, updateSchool, updateCollage, updateUniversity, updateSkills;
    TextView updateImageUri;

    private Button updateData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        updateName = findViewById(R.id.updateNameID);
        updateEmail = findViewById(R.id.updateEmailID);
        updateDescription = findViewById(R.id.updateDescriptionID);
        updateExperience = findViewById(R.id.updateExperienceID);
        updatePhone = findViewById(R.id.updatePhoneID);
        updateDateOfBirth = findViewById(R.id.updateDateOfBirthID);
        updateGender = findViewById(R.id.updateGenderID);
        updateAddress = findViewById(R.id.updateAddressID);
        updateSchool = findViewById(R.id.updateSchoolID);
        updateCollage = findViewById(R.id.updateCollageID);
        updateUniversity = findViewById(R.id.updateUniversityID);
        updateSkills = findViewById(R.id.updateSkillID);
        updateImageUri = findViewById(R.id.imageUriID);


        updateData = findViewById(R.id.updateButton);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference(mAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                updateName.setText(userProfile.userName);
                updateEmail.setText(userProfile.userEmail);
                updateDescription.setText(userProfile.description);
                updateExperience.setText(userProfile.experience);
                updatePhone.setText(userProfile.phone);
                updateDateOfBirth.setText(userProfile.dateOfBirth);
                updateGender.setText(userProfile.gender);
                updateAddress.setText(userProfile.address);
                updateSchool.setText(userProfile.school);
                updateCollage.setText(userProfile.collage);
                updateUniversity.setText(userProfile.university);
                updateSkills.setText(userProfile.skills);
                updateImageUri.setText(userProfile.imageUri);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        updateData.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.updateButton:
                updateUserDetails();
                break;
        }
    }

    private void updateUserDetails() {

        String newName = updateName.getText().toString();
        String newEmail = updateEmail.getText().toString();
        String newDescription = updateDescription.getText().toString();
        String newExperience = updateExperience.getText().toString();
        String newPhone = updatePhone.getText().toString();
        String newDateOfBirth = updateDateOfBirth.getText().toString();
        String newGender = updateGender.getText().toString();
        String newAddress = updateAddress.getText().toString();
        String newSchool = updateSchool.getText().toString();
        String newCollage = updateCollage.getText().toString();
        String newUniversity = updateUniversity.getText().toString();
        String newSkills = updateSkills.getText().toString();
        String newImageUri = updateImageUri.getText().toString();

        if(newName.isEmpty()){
            updateName.setError("Enter your Name");
            updateName.requestFocus();
            return;
        }
        if(newEmail.isEmpty()){
            newEmail=" ";
        }
        if(newGender.isEmpty()){
            updateEmail.setError("Enter your Gender");
            updateEmail.requestFocus();
            return;
        }
        if(newDateOfBirth.isEmpty()){
            newDateOfBirth=" ";
        }
        if(newDescription.isEmpty()){
            newDescription=" ";
        }
        if(newExperience.isEmpty()){
            newExperience=" ";
        }
        if(newPhone.isEmpty()){
            newPhone=" ";
        }
        if(newAddress.isEmpty()){
            newAddress=" ";
        }
        if(newSchool.isEmpty()){
            newSchool=" ";
        }
        if(newCollage.isEmpty()){
            newCollage=" ";
        }
        if(newUniversity.isEmpty()){
            newUniversity=" ";
        }
        if(newSkills.isEmpty()){
            newSkills=" ";
        }


        UserProfile userProfile = new UserProfile(newEmail,newName,newDateOfBirth,newGender,newDescription,newExperience,newPhone,newAddress,newSchool,newCollage,newUniversity,newSkills,newImageUri);

        final DatabaseReference databaseReference = firebaseDatabase.getReference(mAuth.getUid());
        databaseReference.setValue(userProfile);
        finish();
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
