package com.example.car_rent_yassine.adapters;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_rent_yassine.interfaces.OnItemClickListener;
import com.example.car_rent_yassine.R;
import com.example.car_rent_yassine.model.Booking;
import com.example.car_rent_yassine.model.Car;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    @NonNull
    Context context;
    private ArrayList<Booking> bookingarraylist;
    public BookingAdapter(ArrayList<Booking> bookingarraylist) {
        this.bookingarraylist = bookingarraylist;
    }
    private static OnItemClickListener clickListener;


    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView carname;
        private TextView email;
        private TextView duration;
        Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            carname = itemView.findViewById(R.id.carname);
            email = itemView.findViewById(R.id.email);
            duration = itemView.findViewById(R.id.duration);
            delete = itemView.findViewById(R.id.delete);
            delete.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Log.i("hello", "adapterrrr" + "heree" + getAdapterPosition());

           if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
            // clickListener.onClick(view, getposition());

            /*AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
            alertDialog.setTitle("Leave application?");
            alertDialog.setMessage("Are you sure you want to leave the application?");
            alertDialog.setIcon(R.drawable.logocarent);
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            alertDialog.show();*/

        }
    }






    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookingcard,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Booking booking = bookingarraylist.get(position);
        holder.carname.setText(booking.getCar().getManufacturer() + ", " + booking.getCar().getModel());
        holder.email.setText(booking.getDuration() * booking.getCar().getPrice() +" $");
        holder.duration.setText(String.valueOf("From: " +
                booking.getStartdate().replaceAll("\\s+", "-").toLowerCase()
                + "\nTo: " + booking.getEnddate().replaceAll("\\s+", "-").toLowerCase()));


    }


    @Override
    public int getItemCount() {

        return bookingarraylist.size();
    }



}
