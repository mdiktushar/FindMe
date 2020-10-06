package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText updateName, updateEmail;
    private Button updateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_data);

        mAuth = FirebaseAuth.getInstance();

        updateName = findViewById(R.id.updateNameID);
        updateEmail = findViewById(R.id.updateEmailID);
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

        UserProfile userProfile = new UserProfile(newEmail,newName);

        final DatabaseReference databaseReference = firebaseDatabase.getReference(mAuth.getUid());
        databaseReference.setValue(userProfile);
        finish();
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);
    }
}
