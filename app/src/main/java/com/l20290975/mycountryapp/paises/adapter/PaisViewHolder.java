package com.l20290975.mycountryapp.paises.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.l20290975.mycountryapp.R;

public class PaisViewHolder extends RecyclerView.ViewHolder {
    TextView tvCountryname, tvCountryCapital, tvCountryPopulation;

    ShapeableImageView sivCountryImg;

    public PaisViewHolder(@NonNull View itemView) {
        super(itemView);

        tvCountryname = itemView.findViewById(R.id.countryItemTvCountryName);
        tvCountryCapital = itemView.findViewById(R.id.countryItemTvCountryCapital);
        tvCountryPopulation = itemView.findViewById(R.id.countryItemTvCountryPopulation);

        sivCountryImg = itemView.findViewById(R.id.countryItemSivCountryImg);
    }
}
