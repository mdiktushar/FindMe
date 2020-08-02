package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameSignUp, emailSignUp, passwordSignUp, passwordSignUp2;
    private Button signUpSU, chooseDate;
    private TextView signInSU, viewDate;
    private ProgressBar progressBarSignUp;
    private DatePickerDialog datePickerDialog;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    String name, email, password, password2, gender = null, dateOfBirth = null;
    String description = " ", experience = " ", phone = " ", address = " ", school = " ", collage = " ", university = " ", skills = " ";
    String imageUri = "https://firebasestorage.googleapis.com/v0/b/find-me-d1c5a.appspot.com/o/16363.png?alt=media&token=763c3e44-55a7-4092-84fd-bba058f58d71";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();


        nameSignUp = findViewById(R.id.nameEditTextSignUp);
        emailSignUp = findViewById(R.id.emailEditTextSignUp);
        passwordSignUp = findViewById(R.id.passwordEditTextSignUp);
        passwordSignUp2 = findViewById(R.id.passwordEditTextSignUp2);
        viewDate = findViewById(R.id.spViewDateID);
        radioGroup = findViewById(R.id.radioID);

        signInSU = findViewById(R.id.singInIDSingUP);
        signUpSU = findViewById(R.id.signUpIDSignUp);
        chooseDate = findViewById(R.id.chooseDateID);

        progressBarSignUp = findViewById(R.id.progressID2);

        signUpSU.setOnClickListener(this);
        signInSU.setOnClickListener(this);
        chooseDate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.singInIDSingUP:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.signUpIDSignUp:
                register();
                break;
            case R.id.chooseDateID:
                date();
                break;
        }
    }

    private void date() {

        DatePicker datePicker = new DatePicker(this);
        int day = datePicker.getDayOfMonth();
        int month = (datePicker.getMonth())+1;
        int year = datePicker.getYear();

        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        viewDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        dateOfBirth = dayOfMonth+"/"+(month+1)+"/"+year;
                    }
                },year,month,day);

        datePickerDialog.show();
    }

    private void register() {

        try{
            name = nameSignUp.getText().toString().trim();
            email = emailSignUp.getText().toString().trim();
            password = passwordSignUp.getText().toString().trim();
            password2 = passwordSignUp2.getText().toString().trim();

            //checking the validity of the email
            if(email.isEmpty())
            {
                emailSignUp.setError("Enter an email address");
                emailSignUp.requestFocus();
                return;
            }
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                emailSignUp.setError("Enter a valid email address");
                emailSignUp.requestFocus();
                return;
            }
            if(name.isEmpty())
            {
                nameSignUp.setError("Enter Your Name");
                nameSignUp.requestFocus();
                return;
            }
            if(password.isEmpty())
            {
                passwordSignUp.setError("Enter a password");
                passwordSignUp.requestFocus();
                return;
            }
            if(password2.isEmpty())
            {
                passwordSignUp2.setError("Re-enter the password");
                passwordSignUp2.requestFocus();
                return;
            }
            if (!password.equals(password2)) {
                passwordSignUp2.setError("Password does not match");
                passwordSignUp2.requestFocus();
                return;
            }
            if(dateOfBirth==null){
                Toast.makeText(getApplicationContext(),"Give Your Date of Birth",Toast.LENGTH_LONG).show();
                return;
            }

            int selectedGender = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(selectedGender);
            gender = radioButton.getText().toString();

            progressBarSignUp.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBarSignUp.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                sendEmailVerification();
                            } else {
                                // If sign in fails, display a message to the user.
                                if(task.getException() instanceof FirebaseAuthUserCollisionException)
                                    Toast.makeText(getApplicationContext(),"This email address already have used by a user",Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(getApplicationContext(),"Error : "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Select your Gender",Toast.LENGTH_LONG).show();
        }


    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        sendUserData();

                        Toast.makeText(getApplicationContext(),"Successfully Registered, Verification mail has been send!",Toast.LENGTH_LONG).show();
                        mAuth.signOut();
                        finish();
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Verification male has'nt been send! : "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(mAuth.getUid());

        UserProfile userProfile = new UserProfile(email, name, dateOfBirth, gender, description, experience, phone, address, school, collage, university, skills, imageUri);
        databaseReference.setValue(userProfile);
    }
}
