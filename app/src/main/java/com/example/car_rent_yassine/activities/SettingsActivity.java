package com.example.car_rent_yassine.activities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.car_rent_yassine.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {
    Button signout;

    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Car_Rent_Yassine);

        setContentView(R.layout.settings_activity);


        BottomNavigationView bottom;

        signout = findViewById(R.id.signout);
        bottom = findViewById(R.id.bottom_navigation3);
        bottom.setSelectedItemId(R.id.settings);
        getSupportActionBar().setTitle("Settings");

        // below line is used to check if
        // frame layout is empty or not.


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        googleSignInClient= GoogleSignIn.getClient(SettingsActivity.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);
        firebaseAuth=FirebaseAuth.getInstance();

        // Initialize firebase user

       bottom.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent2 = new Intent(SettingsActivity.this,MainpageActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.booking:
                        Intent intent3 = new Intent(SettingsActivity.this, ViewBookingsActivity.class);
                        startActivity(intent3);
                        return true;
                    case R.id.settings:
                        return true;
                    default: return true;
                }
            }
        });


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out from google
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Check condition
                        if(task.isSuccessful())
                        {
                            // When task is successful
                            // Sign out from firebase
                            firebaseAuth.signOut();

                            // Display Toast
                            Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();
                            Intent intent3 = new Intent(SettingsActivity.this,SigninActivity.class);
                            startActivity(intent3);

                            // Finish activity
                            finish();
                        }
                    }
                });
            }
        });
    }


}
