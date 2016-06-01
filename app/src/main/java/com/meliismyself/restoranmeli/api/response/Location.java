package com.meliismyself.restoranmeli.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Meli Oktavia on 5/11/2016.
 */
public class Location {
    @SerializedName("latitude")
    private ArrayList<String> listLatitude = new ArrayList<>();
    @SerializedName("longitude")
    private ArrayList<String> listLongitude = new ArrayList<>();

    public ArrayList<String> getListLatitude() {
        return listLatitude;
    }

    public void setListLatitude(ArrayList<String> listLatitude) {
        this.listLatitude = listLatitude;
    }

    public ArrayList<String> getListLongitude() {
        return listLongitude;
    }

    public void setListLongitude(ArrayList<String> listLongitude) {
        this.listLongitude = listLongitude;
    }
}
