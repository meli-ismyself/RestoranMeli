package com.meliismyself.restoranmeli;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.meliismyself.restoranmeli.adapter.GridCustomAdapterDetail;
import com.meliismyself.restoranmeli.api.request.DetailRestoranRequest;
import com.meliismyself.restoranmeli.api.response.DetailRestoran;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailRestoranActivity extends AppCompatActivity implements DetailRestoranRequest.OnDetailRestoranRequestListener, OnMapReadyCallback {
    private DetailRestoranRequest mDetailRestoranRequest;

    String deskripsiRestoran, namaRestoran, alamatRestoran;
    ArrayList<String> listFasilitasRestoran;

    TextView tvDeskripsi, tvNamaRestoran, tvAlamatRestoran;
    ImageView ivRestoranDetail;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    Context context;
    private GoogleMap mMap;
    private String longResto, latResto, idRestoran, urlPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_restoran);

        AddArrowBack();
        mDetailRestoranRequest = new DetailRestoranRequest(this);
        GetIntent();
        ivRestoranDetail = (ImageView) findViewById(R.id.iv_restoran_detail);
        Picasso.with(getApplicationContext()).load(urlPhoto).fit().centerCrop().into(ivRestoranDetail);
        tvDeskripsi = (TextView) findViewById(R.id.tv_deskripsi);
        tvNamaRestoran = (TextView) findViewById(R.id.tv_nama_restoran);
        tvAlamatRestoran = (TextView) findViewById(R.id.tv_alamat_restoran);


        mDetailRestoranRequest.callApi(idRestoran);

        SetUpRecyclerView();

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMapAsync(this);

    }

    private void SetUpRecyclerView() {
        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_fasilitas);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        mMap = map;
        System.out.println("++++++++++++++++++++++++ ON MAP READY +++++++++++++++++++++++++=");
        //Toast.makeText(getApplicationContext(), "onMapReady", Toast.LENGTH_SHORT).show();
       /* CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(-6.208763, 106.845599))
                .zoom(14)
                .bearing(0)
                .tilt(45)
                .build();*/

    }

    @Override
    public void onDetailRequestRestoranSuccess(DetailRestoran detailRestoranResponse) {
        //List Deskripsi Restoran dan Fasilitasnya
        namaRestoran = detailRestoranResponse.getData().getNama();
        alamatRestoran = detailRestoranResponse.getData().getAlamat();
        deskripsiRestoran = detailRestoranResponse.getData().getDeskripsi();

        tvNamaRestoran.setText(namaRestoran);
        tvAlamatRestoran.setText(alamatRestoran);
        tvDeskripsi.setText(deskripsiRestoran);

        System.out.println("onDetailRequestRestoranSuccess deskripsi +++++++++++++++++++++++++ >> " + deskripsiRestoran);
        listFasilitasRestoran = new ArrayList<String>();
        for (int i = 0; i < detailRestoranResponse.getData().getListFasilitas().size(); i++) {
            listFasilitasRestoran.add(detailRestoranResponse.getData().getListFasilitas().get(i).getNama());
            System.out.println("onDetailRequestRestoranSuccess+++++++++++++++++++++++++++++++++++>> " + listFasilitasRestoran.get(i));
        }

        longResto = detailRestoranResponse.getData().getLocation().getListLongitude().get(0);
        latResto = detailRestoranResponse.getData().getLocation().getListLatitude().get(0);
        System.out.println("onDetailRequestRestoranSuccess double longResto >>" + Double.parseDouble(longResto) + " latResto>>" + Double.parseDouble(latResto));
        Double longRestoDouble = Double.parseDouble(longResto);
        Double latRestoDouble = Double.parseDouble(latResto);


        mMap.addMarker(new MarkerOptions().position(new LatLng(latRestoDouble, longRestoDouble)).title(namaRestoran));

        //Set Kamera
        LatLngBounds Cafe = new LatLngBounds(
                new LatLng(latRestoDouble, longRestoDouble), new LatLng(latRestoDouble, longRestoDouble));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Cafe.getCenter(), 15));

        System.out.println("onDetailRequestRestoranSuccess long >>" + longResto + " lat>> " + latResto);
        mAdapter = new GridCustomAdapterDetail(listFasilitasRestoran);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDetailRequestRestoranFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    private void AddArrowBack() {
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_24, null);

        upArrow.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void GetIntent() {
        idRestoran = getIntent().getStringExtra("intentIdRestoran");
        urlPhoto = getIntent().getStringExtra("intentUrlPhoto");
        System.out.println("GetIntent idRestoran +++++++++++++++++++++>> " + idRestoran);
        System.out.println("GetIntent urlPhoto +++++++++++++++++++++>> " + urlPhoto);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailRestoranActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
