package com.meliismyself.restoranmeli;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.meliismyself.restoranmeli.adapter.GridCustomAdapter;
import com.meliismyself.restoranmeli.api.request.RestoranRequest;
import com.meliismyself.restoranmeli.api.response.Restoran;
import com.meliismyself.restoranmeli.rvlistener.RecyclerItemClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RestoranRequest.OnRestoranRequestListener{

    private RestoranRequest mRestoranRequest;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    Context context;
    private SwipeRefreshLayout refreshLayout;

    ArrayList<String> listNamaRestoran;
    ArrayList<String> listAlamatRestoran;
    ArrayList<String> listUriPhoto;
    ArrayList<String> listId;
    String uriPhoto, idRestoran;
    public static int screenWidthPicasso, screenHeightPicasso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_main_activity);

        // untuk mendapatkan screen size // tapi ini gak dipake karena udah bisa pake fungsi .fit().centerCrop()
        GetScreenSize();
        RefreshLayout();

        mRestoranRequest = new RestoranRequest(this);

    }

    private void SetUpRecyclerView(){

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_restoran);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int topRowVerticalPosition =
//                        (mRecyclerView == null || mRecyclerView.getChildCount() == 0) ?
//                                0 : mRecyclerView.getChildAt(0).getTop();
//                refreshLayout.setEnabled(topRowVerticalPosition >= 0);
//
//            }
//        });
    }

    private void RefreshLayout(){
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                mRestoranRequest.callApi();
                SetUpRecyclerView();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("onRefresh ++++++++++++++++++++++++++++++++++ ");
                //Toast.makeText(getApplicationContext(), "onRefresh", Toast.LENGTH_LONG).show();
                refreshLayout.setRefreshing(true);

                mRestoranRequest.callApi();

                SetUpRecyclerView();

                //refreshLayout.setRefreshing(false);

            }
        });
    }




    @Override
    public void onRequestRestoranSuccess(Restoran restoranResponse) {
        //tvName.setText(restoranResponse.getListData().get(0).getNama());
        //tvAlamat.setText(restoranResponse.getListData().get(0).getAlamat());
        //Picasso.with(this).load(restoranResponse.getListData().get(0).getUrl_foto()).into(ivRestoran);

        //List Nama Restoran dan Alamatnya
        listNamaRestoran  = new ArrayList<String>();
        listAlamatRestoran = new ArrayList<String>();
        listUriPhoto = new ArrayList<String>();
        listId = new ArrayList<String>();
        for (int i=0; i<restoranResponse.getListData().size(); i++){
            listNamaRestoran.add(restoranResponse.getListData().get(i).getNama());
            listAlamatRestoran.add(restoranResponse.getListData().get(i).getAlamat());
            listUriPhoto.add(restoranResponse.getListData().get(i).getUrl_foto());
            listId.add(restoranResponse.getListData().get(i).getId());
            System.out.println("onRequestRestoranSuccess+++++++++++++++++++++++++++++++++++>> " + restoranResponse.getListData().get(i).getUrl_foto());

        }

        //Picasso.with(this).load(restoranResponse.getListData().get(0).getUrl_foto()).into(ivRestoran);
        // add list into adapter
        mAdapter = new GridCustomAdapter(getApplicationContext(), listUriPhoto, listNamaRestoran, listAlamatRestoran);
        mRecyclerView.setAdapter(mAdapter);

        //RecyclerView Listener
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {

                        uriPhoto = listUriPhoto.get(position);
                        idRestoran = listId.get(position);
                        System.out.println("addOnItemTouchListener +++++++++++++++++++++++++ position >> " + position);
                        System.out.println("addOnItemTouchListener +++++++++++++++++++++++++ urlFoto >> " + uriPhoto);

                        OpenDetailRestoranActivity();
                    }
                })
        );
        if (refreshLayout.isRefreshing()==true){
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRequestRestoranFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void OpenDetailRestoranActivity(){
        Intent intent = new Intent(MainActivity.this, DetailRestoranActivity.class);
        intent.putExtra("intentUrlPhoto", uriPhoto);
        intent.putExtra("intentIdRestoran", idRestoran);
        startActivity(intent);
    }

    private void GetScreenSize(){
        if (Build.VERSION.SDK_INT >= 11) {
            Point size = new Point();
            try {
                this.getWindowManager().getDefaultDisplay().getRealSize(size);
                int screenWidth = size.x;
                int screenHeight = size.y;
                int screenWidtX= (screenWidth/2)-100;
                int screenHeightY= screenHeight/8 ;
                screenWidthPicasso = screenWidtX + 60;
                screenHeightPicasso = screenHeightY;
                System.out.println("GetScreenSize Build.VERSION.SDK_INT >= 11 +++++ screenWidth >> " + screenWidth + "+++++ screenHeight" + screenHeight);
                System.out.println("GetScreenSize Build.VERSION.SDK_INT >= 11 +++++ screenWidthPicasso >> " + screenWidthPicasso + "+++++ screenHeightPicasso" + screenHeightPicasso);

            } catch (NoSuchMethodError e) {
                Log.i("error", "it can't work");
            }

        } else {
            DisplayMetrics metrics = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int screenWidth = metrics.widthPixels;
            int screenHeight = metrics.heightPixels;
            int screenWidtX= (screenWidth/2)-100;
            int screenHeightY= screenHeight/8 ;
            screenWidthPicasso = screenWidtX + 60;
            screenHeightPicasso = screenHeightY;
            System.out.println("GetScreenSize +++++ screenWidth >> " + screenWidth + "+++++ screenHeight" + screenHeight);
            System.out.println("GetScreenSize +++++ screenWidthPicasso >> " + screenWidthPicasso + "+++++ screenHeightPicasso" + screenHeightPicasso);

        }
    }
}
