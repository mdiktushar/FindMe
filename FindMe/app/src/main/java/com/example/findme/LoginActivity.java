package com.example.findme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailTX, passwordTX;
    private TextView signUp, forgetPassword;
    private Button signIn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            finish();
            Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
            startActivity(intent);
        }

        progressBar = findViewById(R.id.progressbar);

        emailTX = findViewById(R.id.emailsingin);
        passwordTX = findViewById(R.id.passwordsingin);

        signIn = findViewById(R.id.signinbutton);

        signUp = findViewById(R.id.signupbutton);
        forgetPassword = findViewById(R.id.forgetPasswordbutton);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signupbutton:
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.signinbutton:
                singin();
                break;
            case R.id.forgetPasswordbutton:
                Intent  intent1 = new Intent(getApplicationContext(),ForgerPassword.class);
                startActivity(intent1);
        }
    }

    private void singin() {
        String email = emailTX.getText().toString().trim();
        String password = passwordTX.getText().toString().trim();

        //checking the validity of the email
        if(email.isEmpty())
        {
            emailTX.setError("Enter an email address");
            emailTX.requestFocus();
            return;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailTX.setError("Enter a valid email address");
            emailTX.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            passwordTX.setError("Enter a password");
            passwordTX.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            checkEmailVerification();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });

    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        Boolean email_flag = firebaseUser.isEmailVerified();
        if(email_flag){
            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
            finish();
            Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Please verify your email",Toast.LENGTH_LONG).show();
            mAuth.signOut();
        }
    }

}
