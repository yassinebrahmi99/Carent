package com.example.car_rent_yassine.activities;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.car_rent_yassine.R;
import com.example.car_rent_yassine.model.Booking;
import com.example.car_rent_yassine.model.Car;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActivity extends AppCompatActivity implements androidx.appcompat.widget.Toolbar.OnMenuItemClickListener{
    Booking booking;
    Car car;
    MaterialToolbar toolbar;
    Button pay;
    long amount;
    TextInputLayout cardnumber;
    TextInputLayout expirydate;
    TextInputLayout ccv;
    TextView amounttopay;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Car_Rent_Yassine);

        setContentView(R.layout.payment_activity);
        getSupportActionBar().hide();


        if (getIntent().getExtras() != null) {
            booking = (Booking) getIntent().getSerializableExtra("booking");
        }





        car = booking.getCar();
        car.setAvailability(false);
        amount = car.getPrice() * booking.getDuration();

        toolbar = findViewById(R.id.paymenttoolbar);
        amounttopay = findViewById(R.id.amounttopay);
        amounttopay.setText(amount + "$");
        toolbar.setTitle(car.getManufacturer() + ", " +car.getModel() + " - Payment");
        toolbar.setOnMenuItemClickListener(this);


        cardnumber = findViewById(R.id.cardnumber);
        expirydate = findViewById(R.id.expiry);
        ccv = findViewById(R.id.ccv);
        pay = findViewById(R.id.paybutton);

        pay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mDatabase = FirebaseDatabase.getInstance();
                mDatabaseReference = mDatabase.getReference("bookings");

                String userId = mDatabaseReference.push().getKey();

                mDatabaseReference.child(userId).setValue(booking);

                Log.d(TAG, "database;;;;; ;;; ");

                showCustomDialog();

               // Intent intent = new Intent(PaymentActivity.this, ViewBookingsActivity .class);
                //startActivity(intent);

            }
        });




    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.exit:
                // Toast.makeText(this,"Option 1",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainpageActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return false;    }

    private void showCustomDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button gohome = dialogView.findViewById(R.id.gohome);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button gobooking = dialogView.findViewById(R.id.gobooking);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Intent intent = new Intent(PaymentActivity.this, MainpageActivity.class);
                startActivity(intent);
            }
        });
        gobooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Intent intent = new Intent(PaymentActivity.this, ViewBookingsActivity.class);
                startActivity(intent);
            }
        });

    }
}
