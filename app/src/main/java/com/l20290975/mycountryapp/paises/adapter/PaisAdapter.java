package com.l20290975.mycountryapp.paises.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.l20290975.mycountryapp.R;
import com.l20290975.mycountryapp.paises.activities.DetailCountryActivity;
import com.l20290975.mycountryapp.services.restcountries.models.Country;

import java.util.List;

public class PaisAdapter extends RecyclerView.Adapter<PaisViewHolder> {

    private  Context context;
    private List<Country> countries;

    private int lastAnimatedItem = -1;
    public PaisAdapter(Context context, List<Country> countries) {
        this.context = context;
        this.countries = countries;
    }

    @NonNull
    @Override
    public PaisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_recycler_item_layout, parent, false );
        return new PaisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaisViewHolder holder, int position) {
        Country pais = countries.get(position);

        try {
            Glide.with(context).load( pais.getFlags().getPNG() ).into(holder.sivCountryImg);

            holder.tvCountryname.setText(pais.getName().getOfficial());

            holder.tvCountryPopulation.setText( String.format("%,d",pais.getPopulation()) );

            holder.tvCountryCapital.setText(pais.getCapital().get(0));

            //Agregar el on Click Listener
            holder.itemView.setOnClickListener( (view) -> {
                Intent intent = new Intent( context, DetailCountryActivity.class );
                intent.putExtra("cca3", pais.getCca3());
                context.startActivity(intent);
            });

            setAnimation(holder.itemView, position);

        } catch (Exception e){}
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastAnimatedItem) {
            //Creando animación
            Animation animation = AnimationUtils.loadAnimation( context, R.anim.fall_down_animation );
            itemView.startAnimation( animation );
            lastAnimatedItem = position;
        }
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }
}
