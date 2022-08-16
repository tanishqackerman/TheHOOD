package com.meow.thehood;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomNavigation extends AppCompatActivity {

    private static final String FROM_SETTINGS_KEY = "meow";
    Toolbar toolbar;
    ImageView newpost;

    BottomNavigationView bottomNavigation;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        replaceFragment(new HomeScreen());
        getSupportActionBar().hide();

        bottomNavigation = findViewById(R.id.bottomNavigationView);
        toolbar = findViewById(R.id.thehoodtoolbar);
        newpost = findViewById(R.id.newpost);

        newpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewPost newPost = new NewPost();
                newPost.show(getSupportFragmentManager(), newPost.getTag());
            }
        });

        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeScreen());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileHomeScreen());
                    break;
                case R.id.explore:
                    replaceFragment(new ExploreScreen());
                    break;
                case R.id.chat:
                    replaceFragment(new MessagesScreen());
                    break;
            }
            return true;
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.toolbarmenu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        if (item.getItemId() == R.id.newpost) {
//            NewPost newPost = new NewPost();
//            newPost.show(getSupportFragmentManager(), newPost.getTag());
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }
}