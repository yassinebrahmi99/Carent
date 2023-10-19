package com.example.adminappcarent;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adminappcarent.model.Car;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;


public class AddCarActivity extends AppCompatActivity {
    TextInputLayout Manufacturer, model, Price, Seats, Doors, Transmission;
    String sManufacturer;
    String smodel;
    int sPrice;
    int sSeats;
    int sDoors;
    String sTransmission;

    Button addcarimage, addcar;
    FirebaseStorage storage;
    StorageReference storageReference;
    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;
    ImageView imgview;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car_activity);
        addcarimage = findViewById(R.id.addcarimage);
        addcar = findViewById(R.id.addcar);
        Manufacturer = findViewById(R.id.Manufacturer);
        model = findViewById(R.id.model);
        Price = findViewById(R.id.Price);
        Seats = findViewById(R.id.Seats);
        Doors = findViewById(R.id.Doors);
        Transmission = findViewById(R.id.Transmission);
        imgview =findViewById(R.id.imgview);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        addcarimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();


            }
        });

        addcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sManufacturer = Manufacturer.getEditText().getText().toString();
                smodel = model.getEditText().getText().toString();
                sPrice = Integer.parseInt(Price.getEditText().getText().toString());
                sSeats = Integer.parseInt(Seats.getEditText().getText().toString());
                sDoors = Integer.parseInt(Doors.getEditText().getText().toString());
                sTransmission = Transmission.getEditText().getText().toString();



                uploadImage();


            }
        });






    }

    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imgview.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


/////////////////////////// upload to firebase

    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            Random ran = new Random();
            int x = ran.nextInt(10000) * ran.nextInt(150);


            String carid = sManufacturer + smodel + sDoors + x;
            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + carid);

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                                   // Log.d("downloadUrl", "" + downloadUrl);

                                    mDatabase = FirebaseDatabase.getInstance();
                                    mDatabaseReference = mDatabase.getReference("cars");

                                    String userId = mDatabaseReference.push().getKey();

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(AddCarActivity.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();


                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful());
                                    Uri downloaduri = urlTask.getResult();
                                    Car carnew = new Car(carid,sPrice,sSeats,sManufacturer,smodel,true,downloaduri.toString(),sDoors,sTransmission);
                                    mDatabaseReference.child(carid).setValue(carnew);

                                    Log.d("downloadUrl", "" + downloaduri);
                                    startActivity(new Intent(AddCarActivity.this
                                            ,Mainpage.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(AddCarActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }


}
