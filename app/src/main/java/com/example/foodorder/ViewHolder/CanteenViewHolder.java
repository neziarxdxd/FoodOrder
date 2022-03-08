package com.example.foodorder.ViewHolder;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.Interface.ItemClickListner;
import com.example.foodorder.R;

public class CanteenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtCanteenName;
    public ItemClickListner listner;
    public CanteenViewHolder(View itemView)
    {
        super(itemView);


        txtCanteenName = itemView.findViewById(R.id.canteen_name);


    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}

