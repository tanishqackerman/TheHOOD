package com.meow.thehood;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {

    TextInputLayout regnamelayout, regusernamelayout, regemaillayout, regpasslayout, regbiolayout;
    Button selectpfp, signup;
    FirebaseAuth mAuth;
    FirebaseDBManager firebaseDBManager = new FirebaseDBManager(this, "profiles");
    boolean ispfp = false;
    Uri uri = null;

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        uri = data.getData();
                        ispfp = true;
//                        FcmNotificationsSender fcmNotificationsSender = new FcmNotificationsSender("/topics/all", enterpdfname.getText().toString(), "Datesheet uploaded", UploadPDF.this, UploadPDF.this);
//                        fcmNotificationsSender.SendNotifications();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        regnamelayout = findViewById(R.id.regnamelayout);
        regusernamelayout = findViewById(R.id.regusernamelayout);
        regemaillayout = findViewById(R.id.regemaillayout);
        regpasslayout = findViewById(R.id.regpasslayout);
        regbiolayout = findViewById(R.id.regbiolayout);
        selectpfp = findViewById(R.id.selectpfp);
        signup = findViewById(R.id.signup);

        selectpfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filedialog();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ispfp) {
                    createUser();
                    firebaseDBManager.createAccount(RegisterPage.this, regnamelayout.getEditText().getText().toString(), regusernamelayout.getEditText().getText().toString(), regemaillayout.getEditText().getText().toString(), regbiolayout.getEditText().getText().toString(), uri);
                }
                else Toast.makeText(RegisterPage.this, "Select Profile Pic First!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createUser() {
        String email = regemaillayout.getEditText().getText().toString();
        String pass = regpasslayout.getEditText().getText().toString();

        if (TextUtils.isEmpty(regnamelayout.getEditText().getText().toString())) {
            regnamelayout.setError("Name cannot be empty");
            regnamelayout.requestFocus();
        } else if (TextUtils.isEmpty(regusernamelayout.getEditText().getText().toString())) {
            regnamelayout.setError("Username cannot be empty");
            regnamelayout.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            regemaillayout.setError("Email cannot be empty");
            regemaillayout.requestFocus();
        } else if (TextUtils.isEmpty(pass)) {
            regpasslayout.setError("Password cannot be empty");
            regpasslayout.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(RegisterPage.this, "You are now a part of TheHOOD", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterPage.this, LoginPage.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterPage.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void filedialog() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent = Intent.createChooser(intent, "Select an Image");
        mGetContent.launch(intent);
    }
}