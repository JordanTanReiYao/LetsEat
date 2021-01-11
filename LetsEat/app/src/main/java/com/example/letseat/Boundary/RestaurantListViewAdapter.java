package com.example.letseat.Boundary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.letseat.Entity.Restaurant;
import com.example.letseat.R;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListViewAdapter extends RecyclerView.Adapter<RestaurantListViewAdapter.myViewHolder> {


    private Context mContext;
    private List<Restaurant> mData;
    RequestOptions option;



    public RestaurantListViewAdapter(Context mContext, List<Restaurant> mData){
        this.mContext = mContext;
        this.mData = mData;

        //Glide req option
        option=new RequestOptions().centerCrop().placeholder(R.drawable.letseatlogo).error(R.drawable.letseatlogo);

    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.restaurant_listview,parent, false);
        final myViewHolder viewHolder = new myViewHolder(view);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, RestaurantsDetailsUI.class);
                i.putExtra("vicinity", mData.get(viewHolder.getAdapterPosition()).getVicinity());
                i.putExtra("restaurant_name", mData.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("restaurant_rating", mData.get(viewHolder.getAdapterPosition()).getRating());
                i.putExtra("restaurant_image", mData.get(viewHolder.getAdapterPosition()).getImage());
                i.putExtra("place_id", mData.get(viewHolder.getAdapterPosition()).getPlaceID());
                i.putExtra("phone_number", mData.get(viewHolder.getAdapterPosition()).getPhoneNumber());
                i.putExtra("opening_hour", mData.get(viewHolder.getAdapterPosition()).getOpeningHours());
                i.putExtra("latitude", mData.get(viewHolder.getAdapterPosition()).getLatitude());
                i.putExtra("longitude", mData.get(viewHolder.getAdapterPosition()).getLongitude());
                i.putExtra("fireBaseID", mData.get(viewHolder.getAdapterPosition()).getFirebaseID());
                i.putExtra("isFave", mData.get(viewHolder.getAdapterPosition()).getIsFavourite());

                mContext.startActivity(i);
                ((Activity)mContext).finish();
            }
        });
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_rating.setText(mData.get(position).getRating() +"");
        Glide.with(mContext).load(mData.get(position).getImage()).apply(option).into(holder.img_thumbnail);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        TextView tv_rating;
        ImageView img_thumbnail;
        LinearLayout view_container;

        public myViewHolder(View itemView){
            super(itemView);
            tv_name = itemView.findViewById(R.id.res_name);
            tv_rating = itemView.findViewById(R.id.rating);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);
            view_container = itemView.findViewById(R.id.container);
        }
    }



}
