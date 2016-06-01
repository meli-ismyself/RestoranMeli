package com.meliismyself.restoranmeli.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Meli Oktavia on 5/10/2016.
 */
public class Restoran {
    @SerializedName("DATA")
    private ArrayList<Data> listData = new ArrayList<>();

    public ArrayList<Data> getListData() {
        return listData;
    }

    public void setListData(ArrayList<Data> listData) {
        this.listData = listData;
    }
}
