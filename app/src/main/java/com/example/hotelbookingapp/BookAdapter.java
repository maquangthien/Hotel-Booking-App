package com.example.hotelbookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import model.Hotel;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.viewholder> {
    ArrayList<Hotel> items;
    Context context;

    public BookAdapter(ArrayList<Hotel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BookAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_book, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.viewholder holder, int position) {
        holder.nameHoteltxt.setText(items.get(position).getHotelName());
        holder.startxt.setText(items.get(position).getStarRating());
        holder.locationtxt.setText(items.get(position).getLocation());

        Glide.with(context)
                .load(items.get(position).getImage())
                .into(holder.imageHotel);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView  nameHoteltxt,startxt,locationtxt;
        ImageView imageHotel;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            nameHoteltxt = itemView.findViewById(R.id.nameHoteltxt);
            startxt = itemView.findViewById(R.id.startxt);
            locationtxt = itemView.findViewById(R.id.locationtxt);
            imageHotel = itemView.findViewById(R.id.imageHotel);

        }
    }
}
