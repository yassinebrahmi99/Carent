package com.example.car_rent_yassine.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_rent_yassine.model.Car;
import com.example.car_rent_yassine.adapters.CarAdapter;
import com.example.car_rent_yassine.interfaces.OnItemClickListener;
import com.example.car_rent_yassine.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.nio.channels.Channel;
import java.util.ArrayList;

public class MainpageActivity extends AppCompatActivity implements OnItemClickListener {

    RecyclerView recyclerView;
    ArrayList<Car> CarList = new ArrayList<Car>();
    private CarAdapter carAdapter;
    BottomNavigationView bottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Car_Rent_Yassine);

        setContentView(R.layout.mainpage);
        getSupportActionBar().hide();
        bottom = findViewById(R.id.bottom_navigation);
        bottom.setSelectedItemId(R.id.home);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);



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
                    //Log.d(TAG, "booking arrayy?LISSTTT111111 " + CarList);

                }
                carAdapter = new CarAdapter(CarList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainpageActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(carAdapter);
                carAdapter.setClickListener(MainpageActivity.this);
                Log.d(TAG, " working here ondatachange ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "booking error " + CarList);

            }
        });







        //prepareCar(CarList);



        bottom.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast toast = Toast.makeText(getApplicationContext(), "HOOMEE", Toast.LENGTH_LONG);
                        toast.show();
                        return true;
                    case R.id.booking:
                        //Toast toast2 = Toast.makeText(getApplicationContext(), "BOOOKING", Toast.LENGTH_LONG);
                        //toast2.show();
                        Intent intent2 = new Intent(MainpageActivity.this,ViewBookingsActivity.class);
                        startActivity(intent2);


                        return true;
                    case R.id.settings:
                        Intent intent3 = new Intent(MainpageActivity.this,SettingsActivity.class);
                        startActivity(intent3);
                        return true;
                    default: return true;
                }
            }
        });


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
       // Log.i("hello", CarList.get(position) + "heree");

/*        String c = String.valueOf(CarList.get(position).getPrice());
       Toast toast = Toast.makeText(getApplicationContext(), c, Toast.LENGTH_LONG);
        toast.show();*/
        Intent intent = new Intent(this,ViewCarActivity.class);
        intent.putExtra("car", CarList.get(position)); //where user is an instance of User object
        startActivity(intent);



    }


}
