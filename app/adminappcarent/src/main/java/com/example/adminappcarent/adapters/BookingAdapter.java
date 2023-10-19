package com.example.adminappcarent.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.adminappcarent.BookingsActivity;
import com.example.adminappcarent.OnItemClickListener;
import com.example.adminappcarent.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminappcarent.model.Booking;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    @NonNull
    Context context;
    private ArrayList<Booking> bookingarraylist;
    public BookingAdapter(ArrayList<Booking> bookingarraylist) {
        this.bookingarraylist = bookingarraylist;
    }
    private static OnItemClickListener clickListener;


    public void setClickListener(BookingsActivity itemClickListener) {
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
