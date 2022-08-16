package com.meow.thehood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    Button signin, signup;
    TextInputLayout regemaillayout, regpasslayout;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().hide();

        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
        regemaillayout = findViewById(R.id.regemaillayout);
        regpasslayout = findViewById(R.id.regpasslayout);

        mAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, RegisterPage.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(getApplicationContext(), BottomNavigation.class);
            startActivity(intent);
        }
    }

    private void loginUser() {
        String email = regemaillayout.getEditText().getText().toString();
        String pass = regpasslayout.getEditText().getText().toString();

        if (TextUtils.isEmpty(email)) {
            regemaillayout.setError("Email cannot be empty");
            regemaillayout.requestFocus();
        } else if (TextUtils.isEmpty(pass)) {
            regpasslayout.setError("Password cannot be empty");
            regpasslayout.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    startActivity(new Intent(LoginPage.this, BottomNavigation.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginPage.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}