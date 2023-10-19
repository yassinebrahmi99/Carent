package com.example.adminappcarent;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminappcarent.adapters.CarAdapter;
import com.example.adminappcarent.model.Booking;
import com.example.adminappcarent.model.Car;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Set;

public class Mainpage extends AppCompatActivity implements OnItemClickListener{
    RecyclerView recyclerView;
    ArrayList<Car> CarList = new ArrayList<Car>();
    private CarAdapter carAdapter;
    FloatingActionButton addcar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        BottomNavigationView bottom;
        getSupportActionBar().hide();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerviewadmin);

        bottom = findViewById(R.id.bottom_navigation2);
        bottom.setSelectedItemId(R.id.booking);
        bottom.setSelectedItemId(R.id.cars);
        addcar = findViewById(R.id.fab);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("cars");
        Query checkUser = reference.orderByChild("doors");
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String clubkey = childSnapshot.getKey();
                    Log.d(TAG, "keyyyyyyyy " + clubkey);

                    Car cardb = childSnapshot.getValue(Car.class);
                    CarList.add(cardb);
                    Log.d(TAG, "booking arrayy?LISSTTT111111 " + CarList);

                }
                carAdapter = new CarAdapter(CarList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Mainpage.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(carAdapter);
                carAdapter.setClickListener(Mainpage.this);
                Log.d(TAG, " working here ondatachange ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "booking error " + CarList);

            }
        });







        addcar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentcar = new Intent(Mainpage.this,AddCarActivity.class);
                        startActivity(intentcar);

                    }
                });


        bottom.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cars:

                        return true;
                    case R.id.booking:
                        Intent intent2 = new Intent(Mainpage.this,BookingsActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.settings:
                        Intent intent3 = new Intent(Mainpage.this, SettingActivity.class);
                        startActivity(intent3);
                        return true;
                    default: return true;
                }
            }
        });



       // prepareCar(CarList);








    }


  /*  private void prepareCar (ArrayList array){
        Car car1 = new Car(50,4,"Tesla","3x",true,R.drawable.tesla,4,"Automatic");
        Car car2 = new Car(80,4,"Polo","5",true,R.drawable.polo,5,"Manual");
        Car car3 = new Car(60,4,"Volvo","a32",true,R.drawable.volvo,6,"Manual");
        Car car4 = new Car(100,4,"mercedes","A",true,R.drawable.mercedes,7,"Automatic");
        array.add(car1);
        array.add(car2);
        array.add(car3);
        array.add(car4);

    }*/
    @Override
    public void onClick(View view, int position) {
        Car selected = CarList.get(position);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("cars");
        Query checkUser = reference.orderByChild("carID").equalTo(selected.getCarID());
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
                Log.d(TAG, "booking delete error " + CarList);

            }
        });

    }
}
