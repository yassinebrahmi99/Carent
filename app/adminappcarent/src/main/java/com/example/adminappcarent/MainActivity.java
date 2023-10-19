package com.example.adminappcarent;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextInputLayout emai;
    TextInputLayout passwor;
    Button signin;
    String password;
    String email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emai = findViewById(R.id.email);
        passwor = findViewById(R.id.password);
        signin = findViewById(R.id.signinb);
        mAuth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                email = emai.getEditText().getText().toString();
                password = passwor.getEditText().getText().toString();
                Log.d(TAG, "here 11111");
                FirebaseUser user = mAuth.getCurrentUser();

                if(!(user==null)){
                    startActivity(new Intent(MainActivity.this
                            ,Mainpage.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                if( !(email.isEmpty() || password.isEmpty())){
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        startActivity(new Intent(MainActivity.this
                                                ,Mainpage.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                       // updateUI(null);
                                    }
                                }
                            });}


            }
        });

    }
}