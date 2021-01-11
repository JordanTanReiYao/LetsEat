package com.example.letseat.Boundary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.letseat.Control.Favourites;
import com.example.letseat.Entity.Restaurant;
import com.example.letseat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FavouritesUI extends Fragment {


    Context mContext;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<String> listFavourites;
    private List<Restaurant> listRestaurant;
    DatabaseReference databaseReference;
    FavouritesUI (Context mContext){
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_favourties_ui, container, false);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users/"+user.getUid()+"/Favourites");
        recyclerView = v.findViewById(R.id.recyclerviewidFavourites);
        listFavourites = new ArrayList<>();
        listRestaurant = new ArrayList<>();

        return v;
    }

    public void setUpRecyclerView(List<Restaurant> listRestaurant) {
        RestaurantListViewAdapter myAdapter = new RestaurantListViewAdapter(mContext, listRestaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(myAdapter);
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);
    }


    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listFavourites.clear();
                for (DataSnapshot favouriteSnapshot : dataSnapshot.getChildren()){
                    Restaurant restaurant = new Restaurant();
                    restaurant = favouriteSnapshot.getValue(Restaurant.class);
                    System.out.println("Lets Eat restaurant details: " + restaurant.getName() + restaurant.getRating());
                    restaurant.setIsFavourite(true);
                    listRestaurant.add(restaurant);
                }
                setUpRecyclerView(listRestaurant);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Lets Eat FavouritesUI error: " + databaseError);
            }
        });
    }
}
