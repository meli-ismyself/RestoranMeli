package com.meliismyself.restoranmeli.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Meli Oktavia on 5/10/2016.
 */
public class Fasilitas {
    @SerializedName("nama")
    private String nama;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
