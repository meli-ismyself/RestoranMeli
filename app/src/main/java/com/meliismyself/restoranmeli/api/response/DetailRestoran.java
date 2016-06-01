package com.meliismyself.restoranmeli.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Meli Oktavia on 5/10/2016.
 */
public class DetailRestoran {
    @SerializedName("DATA")
    private DataDetail data ;

    public DataDetail getData() {
        return data;
    }

    public void setData(DataDetail data) {
        this.data = data;
    }
}
