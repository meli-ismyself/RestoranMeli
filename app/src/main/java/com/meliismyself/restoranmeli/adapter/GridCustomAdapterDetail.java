package com.meliismyself.restoranmeli.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meliismyself.restoranmeli.R;
import com.meliismyself.restoranmeli.rvitem.ImageLocationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meli Oktavia on 5/10/2016.
 */


public class GridCustomAdapterDetail extends RecyclerView.Adapter<GridCustomAdapterDetail.ViewHolder> {
    List<ImageLocationItem> mItems ;

    public GridCustomAdapterDetail(ArrayList<String> arrayListFasilitasRestoran) {
        super();
        mItems = new ArrayList<ImageLocationItem>();
        ImageLocationItem endangeredItem = new ImageLocationItem();
        for(int i=0; i<arrayListFasilitasRestoran.size(); i++)
        {
            endangeredItem = new ImageLocationItem();
            endangeredItem.setFasilitasRestoran(arrayListFasilitasRestoran.get(i));
            mItems.add(endangeredItem);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item_list_fasilitas, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ImageLocationItem endangeredItem = mItems.get(position);
        viewHolder.tvFasilitasRestoran.setText(endangeredItem.getFasilitasRestoran());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        //public TextView tvMainTitle;
        public TextView tvFasilitasRestoran;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFasilitasRestoran = (TextView)itemView.findViewById(R.id.tv_fasilitas_restoran);
        }
    }
}
