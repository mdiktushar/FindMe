package com.example.findme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class FindPeople extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ArrayList<UserProfile> uploadList;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    private EditText searchView;
    private FirebaseStorage firebaseStorage;
    private UserProfile upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_people);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseStorage = FirebaseStorage.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        uploadList = new ArrayList<>();

        progressBar = findViewById(R.id.progress2ID);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                uploadList.clear();

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    upload = dataSnapshot1.getValue(UserProfile.class);
                    upload.setKey(dataSnapshot1.getKey());
                    uploadList.add(upload);
                }

                myAdapter = new MyAdapter(FindPeople.this,uploadList);
                recyclerView.setAdapter(myAdapter);

                myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        UserProfile selectedItem = uploadList.get(position);
                        String key = selectedItem.getKey();

                        //Toast.makeText(getApplicationContext(),key+" "+position,Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(),FindResult.class);
                        intent.putExtra("UserKey",key);
                        startActivity(intent);
                    }
                });

                progressBar.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Error: "+databaseError.getMessage(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });


        searchView = findViewById(R.id.searchID);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String s){
        ArrayList<UserProfile> filterlist = new ArrayList<>();

        for(UserProfile name : uploadList){
            if(name.getUserName().toLowerCase().contains(s.toLowerCase())){
                filterlist.add(name);
            }
        }
        myAdapter.filterList(filterlist);
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
