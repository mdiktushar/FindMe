package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class Search extends AppCompatActivity implements View.OnClickListener {

    private EditText id;
    private Button search;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = findViewById(R.id.findUserID);
        search = findViewById(R.id.searchID);
        key = id.getText().toString();

        try {
            search.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.searchID:
                key = id.getText().toString();
                Intent intent = new Intent(getApplicationContext(), FindResult.class);
                intent.putExtra("UserKey", key);
                startActivity(intent);

                break;
        }
    }
}