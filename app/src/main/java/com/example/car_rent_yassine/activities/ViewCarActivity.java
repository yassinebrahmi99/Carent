package com.example.car_rent_yassine.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.car_rent_yassine.model.Car;
import com.example.car_rent_yassine.R;

public class ViewCarActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{
    ImageView img;
    Car car;
    Toolbar toolbar;
    TextView carprice;
    TextView seats;
    TextView transmission;
    TextView doors;
    Button rentbutton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Car_Rent_Yassine);

        setContentView(R.layout.view_car_activity);
        getSupportActionBar().hide();

        carprice = findViewById(R.id.pricecar);
        seats = findViewById(R.id.seats);
        doors = findViewById(R.id.doors);
        transmission = findViewById(R.id.transmission);
        rentbutton = findViewById(R.id.rentbutton);

        img = findViewById(R.id.img);
        if (getIntent().getExtras() != null) {
            car = (Car) getIntent().getSerializableExtra("car");

        }

        Glide.with(ViewCarActivity.this).load(Uri.parse(car.getVehicleImageURL())).into(img);

       // img.setBackgroundResource(car.getVehicleImageURL());
        carprice.setText(String.valueOf(car.getPrice()) + "$/Day");
        seats.setText(String.valueOf(car.getSeats()));
        doors.setText(String.valueOf(car.getDoors()));
        transmission.setText(car.getTransmission());

        rentbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ViewCarActivity.this, BookingActivity.class);
                intent.putExtra("car", car); //where user is an instance of User object
                startActivity(intent);

            }
        });



        toolbar = findViewById(R.id.viewcartoolbar);
        toolbar.setTitle(car.getManufacturer() + ", " +car.getModel());
        toolbar.setOnMenuItemClickListener(this);


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
        return false;
    }



    }

