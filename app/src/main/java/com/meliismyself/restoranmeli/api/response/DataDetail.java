package com.meliismyself.restoranmeli.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Meli Oktavia on 5/10/2016.
 */
public class DataDetail {
    @SerializedName("deskripsi")
    private String deskripsi;
    @SerializedName("fasilitas")
    private ArrayList<Fasilitas> listFasilitas = new ArrayList<>();
    @SerializedName("nama")
    private String nama;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("location")
    private Location location;

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public ArrayList<Fasilitas> getListFasilitas() {
        return listFasilitas;
    }

    public void setListFasilitas(ArrayList<Fasilitas> listFasilitas) {
        this.listFasilitas = listFasilitas;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
