package com.meow.thehood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meow.thehood.Models.ProfileData;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DeleteAccountPage extends AppCompatActivity {

    TextInputLayout regemaillayout, regpasslayout;
    Button deleteaccountbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account_page);
        getSupportActionBar().hide();

        regemaillayout = findViewById(R.id.regemaillayout);
        regpasslayout = findViewById(R.id.regpasslayout);
        deleteaccountbtn = findViewById(R.id.deleteaccountbtn);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        deleteaccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount(regpasslayout.getEditText().getText().toString());
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("profiles");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            ProfileData profileData = dataSnapshot.getValue(ProfileData.class);
                            if (Objects.equals(user.getEmail(), profileData.getEmail())) {
                                databaseReference.child(profileData.getUsername()).removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                startActivity(new Intent(DeleteAccountPage.this, LoginPage.class));
            }
        });
    }

    private void deleteAccount(String password) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(DeleteAccountPage.this, "You left TheHOOD", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(DeleteAccountPage.this, "meow", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
    }
}