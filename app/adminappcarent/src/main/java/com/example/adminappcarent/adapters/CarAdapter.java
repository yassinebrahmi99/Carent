package com.example.adminappcarent.adapters;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.adminappcarent.Mainpage;
import com.example.adminappcarent.OnItemClickListener;
import com.example.adminappcarent.R;
import com.example.adminappcarent.databinding.ActivityMainBinding;
import com.example.adminappcarent.model.Car;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    @NonNull
    private Context context;
    private static ArrayList<Car> carArrayList;
    private static ArrayList<Car> carArrayListcopy = carArrayList;
    public CarAdapter(ArrayList<Car> carArrayList) {
        this.carArrayList = carArrayList;
    }
    private static OnItemClickListener clickListener;
    ActivityMainBinding binding;
    StorageReference storageReference;
    ProgressDialog progressdia;


    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView carname;
        private TextView carmanuf;
        private TextView carprice;
        private ImageView image;
        private CardView cardView;
        private TextView seats;
        private TextView doors;
        private TextView transmission;
        private Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.delete);
            carname = itemView.findViewById(R.id.carname);
            image = itemView.findViewById(R.id.carimage);
            cardView = itemView.findViewById(R.id.cardview);
            carprice = itemView.findViewById(R.id.carprice);
            seats = itemView.findViewById(R.id.seats);
            doors = itemView.findViewById(R.id.doors);
            transmission = itemView.findViewById(R.id.transmission);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i("hello", "adapterrrr" + "heree" + getAdapterPosition());

           if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
           //clickListener.onClick(view, getposition());
        }
        }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carcard,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        holder.image.setBackgroundResource(R.drawable.erorimage);
        final Car car = carArrayList.get(position);

        Glide.with(holder.itemView.getContext()).load(Uri.parse(car.getVehicleImageURL())).into(holder.image);


        Log.i("IMGGG URLLL: ", car.getVehicleImageURL());

        holder.carname.setText(car.getManufacturer() + ", " + car.getModel());
        holder.carprice.setText(car.getPrice() + "$/day");
        holder.seats.setText(String.valueOf(car.getSeats()));
        holder.doors.setText(String.valueOf(car.getDoors()));
        holder.transmission.setText(car.getTransmission());


        /////////////////////////////////////////////////////////////
       // TO BE BACKK TOOOOOOOOO holder.image.setBackgroundResource(car.getVehicleImageURL());
        /////////////////////////////////////////////////////////////

    }


    @Override
    public int getItemCount() {

        return carArrayList.size();
    }






}
