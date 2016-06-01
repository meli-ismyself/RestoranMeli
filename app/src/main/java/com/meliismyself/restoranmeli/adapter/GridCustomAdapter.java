package com.meliismyself.restoranmeli.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meliismyself.restoranmeli.R;
import com.meliismyself.restoranmeli.rvitem.ImageLocationItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meli Oktavia on 5/10/2016.
 */


public class GridCustomAdapter extends RecyclerView.Adapter<GridCustomAdapter.ViewHolder>  {
    List<ImageLocationItem> mItems ;
    private Context context;

    public GridCustomAdapter(Context context, ArrayList<String> arrayListImageRestoran, ArrayList<String> arrayListNamaRestoran, ArrayList<String> arrayListAlamatRestoran) {
        super();
        this.context = context;
        mItems = new ArrayList<ImageLocationItem>();
        ImageLocationItem endangeredItem = new ImageLocationItem();
        for(int i=0; i<arrayListNamaRestoran.size(); i++)
        {
            endangeredItem = new ImageLocationItem();
            endangeredItem.setNamaRestoran(arrayListNamaRestoran.get(i));
            endangeredItem.setAlamatRestoran(arrayListAlamatRestoran.get(i));
            endangeredItem.setImgRestoran(arrayListImageRestoran.get(i));
            mItems.add(endangeredItem);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item_list_restoran, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ImageLocationItem endangeredItem = mItems.get(position);
        viewHolder.tvNamaRestoran.setText(endangeredItem.getNamaRestoran());
        viewHolder.tvAlamatRestoran.setText(endangeredItem.getAlamatRestoran());
        System.out.println("onBindViewHolder++++++++++++++++++++++++++>> "+endangeredItem.getAlamatRestoran());
        Picasso.with(context).load(endangeredItem.getImgRestoran()).fit().centerCrop().into(viewHolder.ivRestoran);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivRestoran;
        public TextView tvNamaRestoran;
        public TextView tvAlamatRestoran;

        public ViewHolder(View itemView) {
            super(itemView);
            ivRestoran = (ImageView)itemView.findViewById(R.id.ivRestoran);
            tvNamaRestoran = (TextView)itemView.findViewById(R.id.tv_nama_restoran);
            tvAlamatRestoran = (TextView)itemView.findViewById(R.id.tv_alamat_restoran);
        }
    }


}
