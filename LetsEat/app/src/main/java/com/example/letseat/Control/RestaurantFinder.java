package com.example.letseat.Control;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.example.letseat.Entity.Restaurant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantFinder {
    private Context mContext;
    private RequestQueue requestQueue;

    public RestaurantFinder(Context mContext) {
        this.mContext = mContext;
    }


    public Restaurant getRestaurantDetails (JSONArray array, int j){
        Restaurant restaurant = new Restaurant();
        try {
            JSONObject js = array.getJSONObject(j);
            JSONObject directions = js.getJSONObject("geometry");
            JSONObject locations = directions.getJSONObject("location");
            double latitude = locations.getDouble("lat");
            double longitude = locations.getDouble("lng");
            String name = js.getString("name");
            Double ratings = js.getDouble("rating");
            JSONArray photosArray = js.getJSONArray("photos");
            String vicinity = js.getString("vicinity");
            String place_id = js.getString("place_id");
            String photoReference = photosArray.getJSONObject(0).getString("photo_reference");
            String photoURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=9999&photoreference="
                    + photoReference + "&key=" + "AIzaSyCASFhDvrsbmhzoLiAcQtiAe_Y4ZM4WXCA";
            restaurant.setName(name);
            restaurant.setRating(ratings);
            restaurant.setImage(photoURL);
            restaurant.setVicinity(vicinity);
            restaurant.setPlaceID(place_id);
            restaurant.setLatitude(latitude);
            restaurant.setLongitude(longitude);
            restaurant.setIsFavourite(false);
            restaurant.setFirebaseID("None");
        }catch (Exception e){
            System.out.println("Lets Eat error: " + e);
        }
        return restaurant;
    }


    public Restaurant getRestaurantPlacesDetails (Restaurant restaurant, JSONObject response){
        try {
            ArrayList<String> openingHrStringArray = new ArrayList<>();
            JSONObject js = response.getJSONObject("result");
            String phone_num = js.getString("formatted_phone_number");
            try {
                JSONObject c = js.getJSONObject("opening_hours");
                JSONArray openingHrArray = c.getJSONArray("weekday_text");
                for (int i = 0; i < openingHrArray.length(); i++) {
                    openingHrStringArray.add(openingHrArray.get(i).toString());
                }
            } catch (Exception e) {
                openingHrStringArray.add("Opening hours not available");
            }
            restaurant.setPhoneNumber(phone_num);
            restaurant.setOpeningHours(openingHrStringArray);
        }catch (Exception e ){
            System.out.println("Lets Eat error: " + e);
        }
        return restaurant;
    }



}
