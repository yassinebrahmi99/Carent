package com.example.car_rent_yassine.activities;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.bumptech.glide.Glide;
import com.example.car_rent_yassine.R;
import com.example.car_rent_yassine.model.Booking;
import com.example.car_rent_yassine.model.Car;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BookingActivity extends AppCompatActivity implements androidx.appcompat.widget.Toolbar.OnMenuItemClickListener{
    private Button mPickDateButton;
    TextView mShowSelectedDateText;
    ImageView img;
    int startdate,enddate;
    MaterialToolbar toolbar;
    Button confirmdate;
    String range="";
    Car car;
    Date date1;
    Date date2;
    String datef1,datef2;
    long diff;
    String userEmail;
    MaterialDatePicker materialDatePicker;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Car_Rent_Yassine);

        setContentView(R.layout.booking_activity);
        getSupportActionBar().hide();

        if (getIntent().getExtras() != null) {
            car = (Car) getIntent().getSerializableExtra("car");
        }

        mPickDateButton = findViewById(R.id.pick_date_button);
        mShowSelectedDateText = findViewById(R.id.show_selected_date);
        img = findViewById(R.id.img);
        confirmdate = findViewById(R.id.confirmdates);

        img.setBackgroundResource(R.drawable.erorimage);
        Glide.with(BookingActivity.this).load(Uri.parse(car.getVehicleImageURL())).into(img);

        toolbar = findViewById(R.id.bookingtoolbar);
        toolbar.setTitle(car.getManufacturer() + ", " +car.getModel());
        toolbar.setOnMenuItemClickListener(this);



        MaterialDatePicker.Builder<Pair<Long, Long>> pairBuilder = MaterialDatePicker.Builder.dateRangePicker();
        pairBuilder.setSelection(androidx.core.util.Pair.<Long, Long>create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()));
        materialDatePicker = pairBuilder.build();

        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

                    }
                });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());

                 date1 = new Date(selection.first);
                 date2 = new Date(selection.second);

                 datef1 = dateFormat.format(date1).replaceAll("\\s+", "-").toLowerCase();
                 datef2 = dateFormat.format(date2).replaceAll("\\s+", "-").toLowerCase();

                long diffInMillies = Math.abs(date1.getTime() - date2.getTime());
                diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);


               mShowSelectedDateText.setText("Selected Date\n \nFrom: " + datef1 +" \n \nTo: " + datef2);

                mPickDateButton.setText("Change selected date");
               confirmdate.setVisibility(View.VISIBLE);

            }
        });


        confirmdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    userEmail = user.getEmail();
                    Log.d(TAG, "database working " + userEmail);

                } else {
                    Log.d(TAG, "database not working ");

                }

                Booking booking = new Booking(datef1,datef2,diff,car,userEmail);
                Log.d(TAG, "database working " + booking.getEmail());
                Intent intent = new Intent(BookingActivity.this, PaymentActivity.class);
                intent.putExtra("booking", booking);
                startActivity(intent);

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
        return false;
    }
}
