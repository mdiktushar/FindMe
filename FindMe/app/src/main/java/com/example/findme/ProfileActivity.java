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
    private TextView proName, proEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        proEmail = findViewById(R.id.proEmailID);
        proName = findViewById(R.id.proNameID);
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
        }else if(item.getItemId()==R.id.changePasswordID){
            Intent intent = new Intent(getApplicationContext(),ChangePassword.class);
            startActivity(intent);
        }else if(item.getItemId()==R.id.photoItem){
            Intent intent = new Intent(getApplicationContext(),UploadPhoto.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}