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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_rent_yassine.R;
import com.example.car_rent_yassine.adapters.BookingAdapter;
import com.example.car_rent_yassine.interfaces.OnItemClickListener;
import com.example.car_rent_yassine.model.Booking;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewBookingsActivity extends AppCompatActivity implements OnItemClickListener {
    RecyclerView recyclerViewbooking;
    ArrayList<Booking> bookingArrayList = new ArrayList<Booking>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Car_Rent_Yassine);

        setContentView(R.layout.view_bookings_activity);
        getSupportActionBar().hide();
        recyclerViewbooking = (RecyclerView)findViewById(R.id.recyclerviewbookings);

        BottomNavigationView bottom;
        /// Test method

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bookings");
        Query checkUser = reference.orderByChild("email").equalTo(user.getEmail());
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String clubkey = childSnapshot.getKey();
                    Log.d(TAG, "keyyyyyyyy " + clubkey);

                    Booking bookingfromdb = childSnapshot.getValue(Booking.class);
                    bookingArrayList.add(bookingfromdb);
                    Log.d(TAG, "booking arrayy?LISSTTT111111 " + bookingArrayList);

                }
                BookingAdapter bookingAdapter = new BookingAdapter(bookingArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewBookingsActivity.this);
                recyclerViewbooking.setLayoutManager(layoutManager);
                recyclerViewbooking.setAdapter(bookingAdapter);
                bookingAdapter.setClickListener(ViewBookingsActivity.this);
                Log.d(TAG, " working here ondatachange ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "booking error " + bookingArrayList);

            }
        });

        bottom = findViewById(R.id.bottom_navigation2);
        bottom.setSelectedItemId(R.id.booking);
        bottom.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent2 = new Intent(ViewBookingsActivity.this,MainpageActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.booking:

                        return true;
                    case R.id.settings:
                        Intent intent3 = new Intent(ViewBookingsActivity.this,SettingsActivity.class);
                        startActivity(intent3);
                        return true;
                    default: return true;
                }
            }
        });

    }







    @Override
    public void onClick(View view, int position) {

        //showCustomDialog(true);



        Booking selected = bookingArrayList.get(position);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bookings");
        Query checkUser = reference.orderByChild("duration").equalTo(selected.getDuration());
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                   // if ((childSnapshot.getValue(Booking.class).getStartdate() == selected.getStartdate()) && (childSnapshot.getValue(Booking.class).getEnddate() == selected.getEnddate())) {
                        Log.d(TAG, " GETTING TO DELETION ");

                        childSnapshot.getRef().removeValue();


                }
                finish();
                startActivity(getIntent());

                Log.d(TAG, " working here ondatachange ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "booking delete error " + bookingArrayList);

            }
        });




    }


    @Override
    protected void onPause() {
        super.onPause();
        try{
            System.out.println("dismissing!!!");
            showCustomDialog(false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

  /*  @Override
    protected void onResume() {
        super.onResume();
        System.out.println("in onresume!!");

        showCustomDialog(true);

    }*/



    private void showCustomDialog(Boolean test) {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.cancel_booking_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button confirm = dialogView.findViewById(R.id.confirm);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button notconfirm = dialogView.findViewById(R.id.notconfirm);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        if (test) {

            alertDialog.show();
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            notconfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    Intent intent = new Intent(ViewBookingsActivity.this, ViewBookingsActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            alertDialog.dismiss();
        }






    }

}
