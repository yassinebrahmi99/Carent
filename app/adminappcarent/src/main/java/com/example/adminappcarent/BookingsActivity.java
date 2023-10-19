package com.example.adminappcarent;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminappcarent.adapters.BookingAdapter;
import com.example.adminappcarent.model.Booking;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookingsActivity extends AppCompatActivity implements OnItemClickListener {
    RecyclerView recyclerViewbooking;
    ArrayList<Booking> bookingArrayList = new ArrayList<Booking>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_activity);
        BottomNavigationView bottom;
        getSupportActionBar().hide();
        bottom = findViewById(R.id.bottom_navigation2);
        bottom.setSelectedItemId(R.id.booking);
        bottom.setSelectedItemId(R.id.cars);
        bottom.setSelectedItemId(R.id.booking);

        recyclerViewbooking = findViewById(R.id.recyclerviewadmin);



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bookings");
        Query checkUser = reference.orderByChild("email");
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
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BookingsActivity.this);
                recyclerViewbooking.setLayoutManager(layoutManager);
                recyclerViewbooking.setAdapter(bookingAdapter);
                bookingAdapter.setClickListener(BookingsActivity.this);
                Log.d(TAG, " working here ondatachange ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "booking error " + bookingArrayList);

            }
        });














        bottom.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cars:
                        Intent intent2 = new Intent(BookingsActivity.this,Mainpage.class);
                        startActivity(intent2);
                        return true;
                    case R.id.booking:
                        return true;

                    case R.id.settings:
                        Intent intent3 = new Intent(BookingsActivity.this, SettingActivity.class);
                        startActivity(intent3);
                        return true;
                    default: return true;
                }
            }
        });


    }

    @Override
    public void onClick(View view, int position) {
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
}
