package com.example.car_rent_yassine.adapters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.car_rent_yassine.interfaces.OnItemClickListener;
import com.example.car_rent_yassine.R;
import com.example.car_rent_yassine.model.Car;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    @NonNull
    private Context context;
    private static ArrayList<Car> carArrayList;
    private static ArrayList<Car> carArrayListcopy = carArrayList;
    public CarAdapter(ArrayList<Car> carArrayList) {
        this.carArrayList = carArrayList;
    }
    private static OnItemClickListener clickListener;



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
            button = itemView.findViewById(R.id.rentnow);
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
           // clickListener.onClick(view, getposition());
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

        final Car car = carArrayList.get(position);
        holder.image.setBackgroundResource(R.drawable.erorimage);

        Glide.with(holder.itemView.getContext()).load(Uri.parse(car.getVehicleImageURL())).into(holder.image);

        holder.carname.setText(car.getManufacturer() + ", " + car.getModel());
        holder.carprice.setText(car.getPrice() + "$/day");
        holder.seats.setText(String.valueOf(car.getSeats()));
        holder.doors.setText(String.valueOf(car.getDoors()));
        holder.transmission.setText(car.getTransmission());
      //  holder.image.setBackgroundResource(car.getVehicleImageURL());

    }


    @Override
    public int getItemCount() {

        return carArrayList.size();
    }






}
