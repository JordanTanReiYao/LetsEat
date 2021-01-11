package com.example.letseat.Control;

public class Favourites {

    private String favouritesID;
    private String placeID;

    public Favourites(String placeID){
        //this.favouritesID = favouritesID;
        this.placeID = placeID;
    }

    public void setFavouritesID(String favouritesID){
        this.favouritesID = favouritesID;
    }

    public String getFavouritesID (){
        return this.favouritesID;
    }

    public void setPlaceID(String PlaceID){
        this.placeID = placeID;
    }

    public String getPlaceID (){
        return this.placeID;
    }


}
